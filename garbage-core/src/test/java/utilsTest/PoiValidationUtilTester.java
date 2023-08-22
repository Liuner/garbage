package utilsTest;

import com.own.garbage.core.utils.excel.poi.PoiValidationUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;

import java.io.IOException;

/**
 * @ClassName PoiValidationUtilTester
 * @Description
 * @Author liugs
 * @Date 2023/7/28 0028 14:48
 */
public class PoiValidationUtilTester {

    @Test
    public void TestPoiValidation() throws IOException, InvalidFormatException {
        PoiValidationUtil.getRule();
    }
}
