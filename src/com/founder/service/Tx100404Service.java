package com.founder.service;

import java.util.ArrayList;
import java.util.List;

import cfca.safeguard.Result;
import cfca.safeguard.api.bank.Constants;
import cfca.safeguard.api.bank.bean.tx.upstream.Tx100404;
import cfca.safeguard.api.bank.util.ResultUtil;
import cfca.safeguard.tx.Transaction;
import cfca.safeguard.tx.business.bank.TxInvolvedAccount_Account;

/**
 * 可疑名单上报-涉案账户
 * @date 2016-07-19
 */
public class Tx100404Service extends BaseService{
	
    public Tx100404Service() throws Exception {
		super();
	}

	@Override
	public void execute(String featrue) throws Exception {
        String transSerialNumber = "dmbank140040420160418";
        String fromTGOrganizationId="";

        Tx100404 tx100404 = new Tx100404();
        tx100404.setTransSerialNumber(transSerialNumber);
        tx100404.setApplicationID("111");
        tx100404.setFeatureCode("1001");
        tx100404.setBankID("13421");
        tx100404.setCardNumber("13421");
        tx100404.setAccountName("test");
        tx100404.setIdType("44");
        tx100404.setIdNumber("4444");
        tx100404.setPhoneNumber("6204321321");
        tx100404.setAddress("cfca");
        tx100404.setPostCode("102400");
        tx100404.setAccountOpenPlace("bj");

        List<TxInvolvedAccount_Account> accountList = new ArrayList<TxInvolvedAccount_Account>();
        TxInvolvedAccount_Account txInvolvedAccount_Account = new TxInvolvedAccount_Account();
        txInvolvedAccount_Account.setAccountNumber("1231321");
        txInvolvedAccount_Account.setAccountType("test");
        txInvolvedAccount_Account.setAccountStatus("1");
        txInvolvedAccount_Account.setCurrency("CNY");
        txInvolvedAccount_Account.setCashRemit("11");

        List<Transaction> transactionList = new ArrayList<Transaction>();

        Transaction transaction = new Transaction();
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
        txInvolvedAccount_Account.setTransactionList(transactionList);

        accountList.add(txInvolvedAccount_Account);
        tx100404.setAccountList(accountList);
        tx100404.setReportOrgName("cfca");
        tx100404.setOperatorName("test");
        tx100404.setOperatorPhoneNumber("123456");

        String requestXML = sgBusiness.tx100404(tx100404,fromTGOrganizationId);
        System.out.println(requestXML);
        String responseXML = sgBusiness.sendPackagedRequestXML(requestXML);
        Result result = ResultUtil.chageXMLToResult(responseXML);

        if (Constants.SUCCESS_CODE_VALUE.equals(result.getCode())) {
            System.out.println("可疑名单上报-涉案账户成功");
        } else {
            System.out.println(result.getResponseXML());
            System.out.println("可疑名单上报-涉案账户失败,错误码=" + result.getCode() + ",错误信息=" + result.getDescription());
        }
	}

}
