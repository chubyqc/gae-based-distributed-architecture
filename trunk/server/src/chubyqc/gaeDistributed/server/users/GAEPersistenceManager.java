package chubyqc.gaeDistributed.server.users;

import javax.jdo.Extent;
import javax.jdo.JDOHelper;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import chubyqc.gaeDistributed.server.Logger;



public class GAEPersistenceManager
{
    private static GAEPersistenceManager _instance = new GAEPersistenceManager();
    public static GAEPersistenceManager getInstance() {
        return _instance;
    }
    
    private static final String DATASTORE_NAME = "datastore";
    
    private PersistenceManagerFactory _factory;
    
    private GAEPersistenceManager() {
        _factory = JDOHelper.getPersistenceManagerFactory(DATASTORE_NAME);
    }

    public void save(final User user) {
        new PMBlock() {

            @Override
            void execute() throws Exception
            {
                getPM().makePersistent(user);
            }
            
        };
    }
    
    void close(Iterable<User> users) {
        if (users instanceof Extent<?>) {
            ((Extent<User>)users).getPersistenceManager().close();
        }
    }
    
    @SuppressWarnings("unchecked")
    public Iterable<User> getUsers() {
        return (Iterable<User>)new PMBlock(false) {

            @Override
            void execute() throws Exception
            {
                setResult(getPM().getExtent(User.class));
            }
            
        }.getResult();
    }
    
    public User getUser(final String username) {
    	return (User)new PMBlock(true) {
			@Override
			void execute() throws Exception {
				setResult(getPM().getObjectById(User.class, username));
			}
    	}.getResult();
    }

    boolean contains(final String id) {
        return (Boolean) new PMBlock() {
    
            @Override
            void execute() throws Exception
            {
                try {
                    getPM().getObjectById(User.class, id);
                    setResult(true);
                } catch (JDOObjectNotFoundException e) {
                    setResult(false);
                }
            }
            
        }.getResult();
    }
    
    private static abstract class PMBlock {
        private PersistenceManager _pm;
        private Object _result;
        
        PMBlock(boolean closePM) {
            _pm = getInstance()._factory.getPersistenceManager();
            try {
                execute();
            }
            catch (Exception e)
            {
                Logger.getInstance().error(e);
            } finally {
                if (closePM) {
                    _pm.close();
                }
            }
        }
        
        PMBlock() {
            this(true);
        }
        
        abstract void execute() throws Exception;
        
        PersistenceManager getPM() {
            return _pm;
        }
        
        Object getResult() {
            return _result;
        }
        
        void setResult(Object result) {
            _result = result;
        }
    }
}
