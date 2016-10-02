package org.nudge.elasticstack.bean.rawdata;

import com.nudge.apm.buffer.probe.RawDataProtocol;
import com.nudge.apm.buffer.probe.RawDataProtocol.Layer;
import com.nudge.apm.buffer.probe.RawDataProtocol.MBean;
import com.nudge.apm.buffer.probe.RawDataProtocol.MBeanAttributeInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.nudge.elasticstack.bean.rawdata.MBeanFred.AttributeInfo;
import org.nudge.elasticstack.type.Mbean;

/**
 * 
 * @author Sarah Bourgeois
 * @author Frederic Massart
 *
 */

public class FredBuilder {

	public static List<TransactionFred> buildTransactions(List<RawDataProtocol.Transaction> rawdataTransactions) {
		List<TransactionFred> transactions = new ArrayList<>(rawdataTransactions.size());

		for (RawDataProtocol.Transaction rawdataTransaction : rawdataTransactions) {
			TransactionFred transaction = new TransactionFred();
			transaction.setCode(rawdataTransaction.getCode());
			transaction.setStartTime(rawdataTransaction.getStartTime());
			transaction.setEndTime(rawdataTransaction.getEndTime());
			transaction.setUserIp(rawdataTransaction.getUserIp());
			
			List<LayerFred> layers = new ArrayList<>();
			for (Layer lay : rawdataTransaction.getLayersList()) {
				LayerFred layer = new LayerFred();
				layer.setLayerName(lay.getLayerName());
				layer.setTime(lay.getTime());
				layer.setCount(lay.getCount());
				layers.add(layer);
			}
			transaction.setLayers(layers);
			transactions.add(transaction);
		}
		return transactions;
	}



	public static List<MBeanFred> buildMbeans(List<RawDataProtocol.MBean> rawdataMbeans) {
		List<MBeanFred> mbeanList = new ArrayList<>(rawdataMbeans.size());
		for (RawDataProtocol.MBean rawdataMbean : rawdataMbeans) {
			MBeanFred mbean = new MBeanFred();
			mbean.setAttributeInfoCount(rawdataMbean.getAttributeInfoCount());
			mbean.setCollectingTime(rawdataMbean.getCollectingTime());
			mbean.setObjectName(rawdataMbean.getObjectName());
			mbeanList.add(mbean);
			mbeanList.add(mbean);
		
		List<AttributeInfo> attributelist = new ArrayList<>();
		    for(MBean mb: rawdataMbeans){
			List<MBeanAttributeInfo> gni =	mb.getAttributeInfoList();
				for(MBeanAttributeInfo mba : gni){
					MBeanFred.AttributeInfo attribute = mbean.new AttributeInfo();
					attribute.setNameId(mba.getNameId());
					attribute.setValue(mba.getValue());
					attributelist.add(attribute);		
				}
				}
		    mbean.setAttributeInfos(attributelist);
		    mbeanList.add(mbean);
		}
		    
		    

		return mbeanList;
	}
	
	
} // end of class
	

