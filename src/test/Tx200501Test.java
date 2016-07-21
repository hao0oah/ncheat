package test;

import com.founder.beans.Com;

import cfca.safeguard.api.mps.ClientEnvironment;
import cfca.safeguard.api.mps.SGBusiness;
import cfca.safeguard.api.mps.bean.tx.upstream.Tx200501;
import cfca.safeguard.tx.business.mps.TxCaseInfoPush_Entry;


/**
 * 涉案信息推送-银行账户
 */
public class Tx200501Test {
    
    public static void main(String[] args) throws Exception {
    	ClientEnvironment.initClientEnvironment(Com.FILE_PATH);
        SGBusiness sgBusiness = new SGBusiness();

        Tx200501 tx200501 = new Tx200501();
        String mode = "01";//只能是01
        String to = ClientEnvironment.ROLL_TO;
        String transSerialNumber = "031100000005"+Com.TRUST_CODE+Com.getRandomNo(28);
        
        String fromTGOrganizationId="";
        
        tx200501.setTransSerialNumber(transSerialNumber);
        tx200501.setApplicationID("200501"+Com.getRandomNo(28));
        tx200501.setListType("01");
        tx200501.setApplicationOrgID("01001");
        tx200501.setApplicationTime("20160818120000");
        tx200501.setOperatorIDType("01");
        tx200501.setOperatorIDNumber("222303197705328831");
        tx200501.setOperatorName("徐");
        tx200501.setOperatorPhoneNumber("01061957036");
        tx200501.setInvestigatorIDType("01");
        tx200501.setInvestigatorIDNumber("222303197705328831");
        tx200501.setInvestigatorName("徐");
        
        TxCaseInfoPush_Entry tx200501_Entry=new TxCaseInfoPush_Entry();
        tx200501_Entry.setUpdateType("01");
        tx200501_Entry.setCaseType("11");
        tx200501_Entry.setDataType("AccountNumber");
        tx200501_Entry.setOrganizationID(Com.BANK_CODE);
        tx200501_Entry.setAccountType("01");
        tx200501_Entry.setAccount("6231680000000004525");
        tx200501_Entry.setName("郭丰灶");
        tx200501_Entry.setIDType("01");
        tx200501_Entry.setIDNumber("130924198407162219");
        tx200501_Entry.setPhoneNumber("15595268637");
        tx200501_Entry.setIpAddress("bj");
        tx200501_Entry.setMac("1321321");
        tx200501_Entry.setDeviceID("fdsafdas");
        tx200501_Entry.setQq("1321321");
        tx200501_Entry.setUrl("www.sohu.com");
        tx200501_Entry.setCurrency("CNY");
        tx200501_Entry.setAmount("13213123");
        tx200501_Entry.setAccountOpenTime("20150626052400");
        tx200501_Entry.setVerificationTime("20150626052400-20160626052400");
        tx200501_Entry.setRemark("test");
        tx200501.getList().add(tx200501_Entry);
       
        try {
            sgBusiness.writeUpstreamXml(tx200501,fromTGOrganizationId, mode, to);
            System.out.println("写入文件成功");
        } catch (Exception e) {
            System.out.println("写入文件失败");
            e.printStackTrace();
        }
    }

}
