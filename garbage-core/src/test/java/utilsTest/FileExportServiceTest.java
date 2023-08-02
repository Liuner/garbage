package utilsTest;

import com.own.garbage.core.bo.GarbageReqBaseBo;
import com.own.garbage.core.service.FileExportService;
import com.own.garbage.core.service.impl.FileExportServiceImpl;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;

import java.io.IOException;

/**
 * @ClassName FileExportServiceTest
 * @Description
 * @Author liugs
 * @Date 2023/8/1 0001 17:19
 */
public class FileExportServiceTest {

    @Test
    public void trigger() throws IOException, InvalidFormatException {
        FileExportService fileExportService = new FileExportServiceImpl();
        GarbageReqBaseBo reqBo = new GarbageReqBaseBo();
        reqBo.setFilePath("E:\\User\\windows\\Desktop\\fillDataTemplate.xlsx");
        fileExportService.trigger(reqBo);
    }
}
