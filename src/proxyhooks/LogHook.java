package proxyhooks;

import com.got.proxy.contracts.CallbackHook;

import java.lang.reflect.Method;

public class LogHook implements CallbackHook {
    @Override
    public void before(Object o, Method method, Object[] objects) {
        System.out.println(method.getName() + " is going to be called in " + o.getClass().getSimpleName() + "!");
    }

    @Override
    public void after(Object o, Method method, Object[] objects, Object o1) {
        System.out.println(method.getName() + " was called in " + o.getClass().getSimpleName() + "!");
    }
}
