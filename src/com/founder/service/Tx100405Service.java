package com.founder.service;

import java.util.ArrayList;
import java.util.List;

import cfca.safeguard.Result;
import cfca.safeguard.api.bank.Constants;
import cfca.safeguard.api.bank.bean.tx.upstream.Tx100405;
import cfca.safeguard.api.bank.util.ResultUtil;
import cfca.safeguard.tx.business.bank.TxExceptionalEvent_Account;
import cfca.safeguard.tx.business.bank.TxExceptionalEvent_Accounts;
import cfca.safeguard.tx.business.bank.TxExceptionalEvent_Transaction;

/**
 * 可疑名单上报-异常事件
 * @date 2016-07-19
 */
public class Tx100405Service extends BaseService {
    public Tx100405Service() throws Exception {
		super();
	}

	@Override
	public void execute(String featrue) throws Exception {
        String transSerialNumber = "dmbank140040520160418";
        String fromTGOrganizationId = "";

        Tx100405 tx100405 = new Tx100405();

        tx100405.setTransSerialNumber(transSerialNumber);
        tx100405.setApplicationID("100404");
        tx100405.setBankID("cfca");
        tx100405.setOperatorName("test");
        tx100405.setOperatorPhoneNumber("123456");

        List<TxExceptionalEvent_Accounts> accountsList = new ArrayList<TxExceptionalEvent_Accounts>();
        TxExceptionalEvent_Accounts txExceptionalEvent_Accounts = new TxExceptionalEvent_Accounts();
        txExceptionalEvent_Accounts.setAccountName("test");
        txExceptionalEvent_Accounts.setCardNumber("1321312");
        txExceptionalEvent_Accounts.setRemark("test");

        TxExceptionalEvent_Account txExceptionalEvent_Account = new TxExceptionalEvent_Account();
        txExceptionalEvent_Account.setAccountNumber("1231321");
        txExceptionalEvent_Account.setAccountType("test");
        txExceptionalEvent_Account.setAccountStatus("1");
        txExceptionalEvent_Account.setCurrency("CNY");
        txExceptionalEvent_Account.setCashRemit("11");

        List<TxExceptionalEvent_Transaction> transactionList = new ArrayList<TxExceptionalEvent_Transaction>();

        TxExceptionalEvent_Transaction transaction = new TxExceptionalEvent_Transaction();
        transaction.setFeatureCode("3001"); //事件特征码
        transaction.setTransactionType("01");
        transaction.setBorrowingSigns("daf");
        transaction.setCurrency("RMB");
        transaction.setTransactionAmount("dsaf");
        transaction.setAccountBalance("20");
        transaction.setTransactionTime("20160101000000");
        transaction.setTransactionSerial("113213");
        transaction.setOpponentName("cfca");
        transaction.setOpponentAccountNumber("123456");
        transaction.setOpponentCredentialNumber("123456");
        transaction.setOpponentDepositBankID("adsf");
        transaction.setTransactionRemark("test");
        transaction.setTransactionBranchName("cfca");
        transaction.setTransactionBranchCode("fda");
        transaction.setLogNumber("123456");
        transaction.setSummonsNumber("123456");
        transaction.setVoucherType("01");
        transaction.setVoucherCode("adf");
        transaction.setCashMark("fdsa");
        transaction.setTerminalNumber("123456");
        transaction.setTransactionStatus("asdf");
        transaction.setTransactionAddress("fda");
        transaction.setMerchantName("cfca");
        transaction.setMerchantCode("asdf");
        transaction.setIpAddress("adsf");
        transaction.setMac("adsf");
        transaction.setTellerCode("adsf");
        transaction.setRemark("test");

        transactionList.add(transaction);
        txExceptionalEvent_Account.setTransactionList(transactionList);

        List<TxExceptionalEvent_Account> txInvolvedAccount_AccountList = new ArrayList<TxExceptionalEvent_Account>();
        txInvolvedAccount_AccountList.add(txExceptionalEvent_Account);
        
        txExceptionalEvent_Accounts.setAccountList(txInvolvedAccount_AccountList);

        accountsList.add(txExceptionalEvent_Accounts);
        
        tx100405.setAccountsList(accountsList);

        String requestXML = sgBusiness.tx100405(tx100405, fromTGOrganizationId);
        System.out.println(requestXML);
        String responseXML = sgBusiness.sendPackagedRequestXML(requestXML);
        Result result = ResultUtil.chageXMLToResult(responseXML);

        if (Constants.SUCCESS_CODE_VALUE.equals(result.getCode())) {
            System.out.println("可疑名单上报-异常事件成功");
        } else {
            System.out.println(result.getResponseXML());
            System.out.println("可疑名单上报-异常事件失败,错误码=" + result.getCode() + ",错误信息=" + result.getDescription());
        }
	}

}
