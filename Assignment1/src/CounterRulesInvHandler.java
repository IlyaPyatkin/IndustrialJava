import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class CounterRulesInvHandler implements InvocationHandler {
    public String counterRulesClassName;

    public CounterRulesInvHandler(String counterRulesClassName) {
        this.counterRulesClassName = counterRulesClassName;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object counter = new MyLoader()
                .loadClass(counterRulesClassName)
                .newInstance();
        return method.invoke(counter, args);
    }
}
