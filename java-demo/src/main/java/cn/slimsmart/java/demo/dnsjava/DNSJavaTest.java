package cn.slimsmart.java.demo.dnsjava;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Iterator;
import java.util.List;

import org.xbill.DNS.ARecord;
import org.xbill.DNS.DClass;
import org.xbill.DNS.Flags;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.Message;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.Resolver;
import org.xbill.DNS.Section;
import org.xbill.DNS.SimpleResolver;
import org.xbill.DNS.TSIG;
import org.xbill.DNS.Type;
import org.xbill.DNS.Update;
import org.xbill.DNS.ZoneTransferIn;

public class DNSJavaTest {

	public static void main(String[] args) throws Exception {
		// query();
		//transferZone();
		addRR();
	}

	// 查询
	static void query() throws IOException {
		Resolver resolver = new SimpleResolver("192.168.36.54");
		resolver.setPort(53);
		Lookup lookup = new Lookup("www.test.com", Type.A);
		lookup.setResolver(resolver);
		lookup.run();
		if (lookup.getResult() == Lookup.SUCCESSFUL) {
			System.out.println(lookup.getAnswers()[0].rdataToString());
		}
	}

	static void transferZone() throws Exception {
		ZoneTransferIn xfr = ZoneTransferIn.newAXFR(new Name("slimsmart.cn."), "192.168.36.54", null);
		List records = xfr.run();
		Message response = new Message();
		response.getHeader().setFlag(Flags.AA);
		response.getHeader().setFlag(Flags.QR);
		// response.addRecord(query.getQuestion(),Section.QUESTION);
		Iterator it = records.iterator();
		while (it.hasNext()) {
			response.addRecord((Record) it.next(), Section.ANSWER);
		}
		System.out.println(response);
	}

	static void addRR() throws Exception {
		Name zone = Name.fromString("slimsmart.cn.");
		Name host = Name.fromString("mail", zone);
		Update update = new Update(zone, DClass.IN);
		Record record = new ARecord(host, DClass.IN, 86400, InetAddress.getByName("121.42.81.20"));
		update.delete(record);
		update.add(record);
		Resolver resolver = new SimpleResolver("192.168.36.54");
		resolver.setPort(53);
		TSIG tsig = new TSIG("waiwang_key", "6Ube2jTRIPxuIBlL5rCg5Q==");
		//TSIG tsig = new TSIG("neiwang_key", "XvbglfmP8aZ20CLEP5NL+w==");
		resolver.setTSIGKey(tsig);
		resolver.setTCP(true);
		Message response = resolver.send(update);
		System.out.println(response);
	}
}
