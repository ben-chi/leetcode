package com.example.demo;

public interface Task {
    void run() throws Exception;
    @SuppressWarnings("unchecked")
     static <T extends Throwable> void throwAs(Throwable t) throws T{
        throw(T) t;
    }
    static Runnable asRunnable(Task task){
        return ()->
        {
            try{
                task.run();
            }
            catch(Exception e){
                Task.<RuntimeException>throwAs(e);
            }
        };
    }
}
