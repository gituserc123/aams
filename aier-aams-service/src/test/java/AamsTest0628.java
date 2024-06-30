import com.aier.cloud.AamsServiceApp;
import com.aier.cloud.biz.aams.controller.AuditFxtsReplyController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes= AamsServiceApp.class)
public class AamsTest0628 {

    @Autowired
    AuditFxtsReplyController auditFxtsReplyController;

    @Test
    public void testAuditFxtsReplyController(){
        auditFxtsReplyController.selectByAuditRecordId(934L);
    }


}