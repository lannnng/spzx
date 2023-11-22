import com.learn.result.Result;
import com.learn.result.ResultCodeEnum;
import org.junit.jupiter.api.Test;


public class ResultTest {

    /*链式调用测试*/
    @Test
    void test01(){
        Result<Object> result = Result.success();
        Result<Object> ss = result.code(55).message("ss").data(null);
        System.out.println(ss);
    }

}
