package com.cyc.util;

/**
 * Email.java
 * 版权所有(C) 2012 
 * 创建:cuiran 2012-05-11 12:52:41
 */
 
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

import com.cyc.config.ConfigManage;
import com.cyc.exception.MyException;
import com.cyc.hibernate.domain.DownLoadRecordDomian;
import com.cyc.hibernate.domain.EbookDomain;
import com.cyc.hibernate.domain.Ed2kStateDomain;

/**
 * 本程序用java来实现Email的发送，所用到的协议为：SMTP，端口号为25；<br>
 * 方法：用Socket进行实现，打开客户端的Socket，并连接上服务器：<br>
 * 如：Socket sockClient = new Socket("smtp.qq.com",23);<br>
 * 这表示发件方连接的是QQ邮箱的服务器，端口号为23
 * 
 * @author cuiran
 * @version 1.0.0
 */
public class JavaEmail {
	/**
	 * 整个MIME邮件对象
	 */
	private MimeMessage mimeMsg;
	/**
	 * 专门用来发送邮件的Session会话
	 */
	private Session session;
	/**
	 * 封装邮件发送时的一些配置信息的一个属性对象
	 */
	private Properties props;
	/**
	 * 发件人的用户名
	 */
	private String username;
	/**
	 * 发件人的密码
	 */
	private String password;
	/**
	 * 用来实现附件添加的组件
	 */
	private Multipart mp;

	/**
	 * 发送参数初始化,有的服务器不需要用户验证，所以这里对用户名和密码进行初始化""
	 * 
	 * @param smtp
	 *            SMTP服务器的地址，比如要用QQ邮箱，哪么应为："smtp.qq.com"，163为："smtp.163.com"
	 */
	public JavaEmail(String smtp) {
		username = "";
		password = "";
		// 设置邮件服务器
		setSmtpHost(smtp);
		// 创建邮件
		createMimeMessage();
	}

	/**
	 * 设置发送邮件的主机(JavaMail需要Properties来创建一个session对象。
	 * 它将寻找字符串"mail.smtp.host"，属性值就是发送邮件的主机);
	 * 
	 * @param hostName
	 */
	public void setSmtpHost(String hostName) {
		System.out.println("设置系统属性：mail.smtp.host = " + hostName);
		if (props == null)
			props = System.getProperties();
		props.put("mail.smtp.host", hostName);
	}

	/**
	 * (这个Session类代表JavaMail 中的一个邮件session. 每一个基于
	 * JavaMail的应用程序至少有一个session但是可以有任意多的session。 在这个例子中,
	 * Session对象需要知道用来处理邮件的SMTP 服务器。
	 */
	public boolean createMimeMessage() {
		try {
			System.out.println("准备获取邮件会话对象！");
			// 用props对象来创建并初始化session对象
			session = Session.getDefaultInstance(props, null);
		} catch (Exception e) {
			System.err.println("获取邮件会话对象时发生错误！" + e);
			return false;
		}
		System.out.println("准备创建MIME邮件对象！");
		try {
			// 用session对象来创建并初始化邮件对象
			mimeMsg = new MimeMessage(session);
			// 生成附件组件的实例
			mp = new MimeMultipart();
		} catch (Exception e) {
			System.err.println("创建MIME邮件对象失败！" + e);
			return false;
		}
		return true;
	}

	/**
	 * 设置SMTP的身份认证
	 */
	public void setNeedAuth(boolean need) {
		System.out.println("设置smtp身份认证：mail.smtp.auth = " + need);
		if (props == null) {
			props = System.getProperties();
		}
		if (need)
			props.put("mail.smtp.auth", "true");
		else
			props.put("mail.smtp.auth", "false");
	}

	/**
	 * 进行用户身份验证时，设置用户名和密码
	 */
	public void setNamePass(String name, String pass) {
		System.out.println("程序得到用户名与密码");
		username = name;
		password = pass;
	}

	/**
	 * 设置邮件主题
	 * 
	 * @param mailSubject
	 * @return
	 */
	public boolean setSubject(String mailSubject) {
		System.out.println("设置邮件主题！");
		try {
			mimeMsg.setSubject(mailSubject);
		} catch (Exception e) {
			System.err.println("设置邮件主题发生错误！");
			return false;
		}
		return true;
	}

	/**
	 * 设置邮件内容,并设置其为文本格式或HTML文件格式，编码方式为UTF-8
	 * 
	 * @param mailBody
	 * @return
	 */
	public boolean setBody(String mailBody) {
		try {
			System.out.println("设置邮件体格式");
			BodyPart bp = new MimeBodyPart();
			bp.setContent(
					"<meta http-equiv=Content-Type content=text/html; charset=UTF-8>"
							+ mailBody, "text/html;charset=UTF-8");
			// 在组件上添加邮件文本
			mp.addBodyPart(bp);
		} catch (Exception e) {
			System.err.println("设置邮件正文时发生错误！" + e);
			return false;
		}
		return true;
	}

	/**
	 * 增加发送附件
	 * 
	 * @param filename
	 *            邮件附件的地址，只能是本机地址而不能是网络地址，否则抛出异常
	 * @return
	 */
	public boolean addFileAffix(String filename) {
		System.out.println("增加邮件附件：" + filename);
		try {
			BodyPart bp = new MimeBodyPart();
			FileDataSource fileds = new FileDataSource(filename);
			bp.setDataHandler(new DataHandler(fileds));
			// 发送的附件前加上一个用户名的前缀
			bp.setFileName(fileds.getName());
			// 添加附件
			mp.addBodyPart(bp);
		} catch (Exception e) {
			System.err.println("增加邮件附件：" + filename + "发生错误！" + e);
			return false;
		}
		return true;
	}

	/**
	 * 设置发件人地址
	 * 
	 * @param from
	 *            发件人地址
	 * @return
	 */
	public boolean setFrom(String from) {
		System.out.println("设置发信人！");
		try {
			mimeMsg.setFrom(new InternetAddress(from));
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 设置收件人地址
	 * 
	 * @param to
	 *            收件人的地址
	 * @return
	 */
	public boolean setTo(String to) {
		System.out.println("设置收信人");
		if (to == null)
			return false;
		try {
			mimeMsg.setRecipients(javax.mail.Message.RecipientType.TO,
					InternetAddress.parse(to));
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 发送附件
	 * 
	 * @param copyto
	 * @return
	 */
	public boolean setCopyTo(String copyto) {
		System.out.println("发送附件到");
		if (copyto == null)
			return false;
		try {
			mimeMsg.setRecipients(javax.mail.Message.RecipientType.CC,
					InternetAddress.parse(copyto));
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 发送邮件
	 * 
	 * @return
	 */
	public boolean sendout() {
		try {
			mimeMsg.setContent(mp);
			mimeMsg.saveChanges();
			System.out.println("正在发送邮件....");
			Session mailSession = Session.getInstance(props, null);
			Transport transport = mailSession.getTransport("smtp");
			// 真正的连接邮件服务器并进行身份验证
			transport.connect((String) props.get("mail.smtp.host"), username,
					password);
			// 发送邮件
			transport.sendMessage(mimeMsg,
					mimeMsg.getRecipients(javax.mail.Message.RecipientType.TO));
			System.out.println("发送邮件成功！");
			transport.close();
		} catch (Exception e) {
			System.err.println("邮件发送失败！" + e.getMessage());
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		Doemail();
//		EbookDomain domain= new EbookDomain();
//		domain.setAuthors("cyc");
//		domain.setTitle("sadfsadfsdf");
//		
//		sendBuyEmailTo("597318121@qq.com", "asdfasdsf", "www.baidu.com",domain );
//		
	}

	public  static void Doemail() {
		/**
		 * 
		 ************* 切切注意********
		 * 
		 * 注意 用此程序发邮件必须邮箱支持smtp服务 在2006年以后申请的163邮箱是不支持的 我知道sina邮箱 sohu邮箱 qq邮箱支持
		 * 但是sina和qq邮箱需要手工设置开启此功能 所以在测试时最好使用这三个邮箱 sina邮箱的smtp设置方法如下 登录sina邮箱
		 * 依次点击 邮箱设置--->帐户--->POP/SMTP设置 将开启复选框选中 然后保存
		 * qq授权码  buylxskbksskbbja
		 ************* 切切注意********
		 */
		JavaEmail themail = new JavaEmail("smtp.sina.com");// 这里以新浪邮箱为例子
		String mailbody = "<a href='http://www.wphtu.ah.cn/' target='_blank'>芜湖职业技术学院</a>发送成功了哦";// 邮件正文
		themail.setNeedAuth(true);
		themail.setSubject("JAVA发邮件的测试");// 邮件主题
		themail.setBody(mailbody);// 邮件正文
		themail.setTo("545646649@qq.com");// 收件人地址
		themail.setFrom("a597318121@sina.com");// 发件人地址
		//themail.addFileAffix("c:/test.txt");// 附件文件路径,例如：C:/222.jpg,*注；"/"的写法；
											// 如果没有可以不写
		themail.setNamePass("a597318121@sina.com", "aa55665566");// 发件人地址和密码
																	// **改为相应邮箱密码
		themail.sendout();
	}

	public static boolean sendBuyEmailTo(String emialto, String buycode, String downpath, EbookDomain ebookDomain)   {
		// TODO Auto-generated method stub
		/**
		 * 
		 ************* 切切注意********
		 * 
		 * 注意 用此程序发邮件必须邮箱支持smtp服务 在2006年以后申请的163邮箱是不支持的 我知道sina邮箱 sohu邮箱 qq邮箱支持
		 * 但是sina和qq邮箱需要手工设置开启此功能 所以在测试时最好使用这三个邮箱 sina邮箱的smtp设置方法如下 登录sina邮箱
		 * 依次点击 邮箱设置--->帐户--->POP/SMTP设置 将开启复选框选中 然后保存
		 * qq授权码  buylxskbksskbbja
		 ************* 切切注意********
		 */
		try {
			JavaEmail themail =  new JavaEmail(ConfigManage.GLOBAL_STRING_CONFIG.get("smt"));// 这里以新浪邮箱为例子
			
			String mailbody = "<a href='http://www.libooc.com' target='_blank'>Libooc免费学术分享</a>全站拥有超过150w的各类外文电子书除部分图书因特殊原因需要收费以外其他全部免费提供给广大互联网爱书的书迷</br>"+
			ebookDomain.getTitle()+"作者"+ebookDomain.getAuthors()+
			"点击下方链接即可下载   <a href='"+downpath+"' target='_blank'>下载链接</a></br>"+
			"<font size='18' color='red'>下载的文件是无后缀的文件请将文件重命名为."+ebookDomain.getExtension()+"结尾文件才能打开</font>"+
					"</br><font size='16pt' color='red'>请记住您的兑书码"+buycode+"下次下载这本书依然可用</font>"+
			"</br>对于近期关闭会员的决定实属无奈望大家谅解过段时间网站会重新开启 不要回复此邮件  ";// 邮件正文
			themail.setNeedAuth(true);
			themail.setSubject("您在www.libooc.com上兑换的电子书到了。学术免费分享网站LIBOOC感谢您对我们的支持。我们会持续为互联网提供更优质的电子书资源分享渠道");// 邮件主题
			themail.setBody(mailbody);// 邮件正文
			themail.setTo(emialto);// 收件人地址
			themail.setFrom(ConfigManage.GLOBAL_STRING_CONFIG.get("emailfrom"));// 发件人地址
			//themail.addFileAffix("c:/test.txt");// 附件文件路径,例如：C:/222.jpg,*注；"/"的写法；
												// 如果没有可以不写
			themail.setNamePass(ConfigManage.GLOBAL_STRING_CONFIG.get("emailfrom"),ConfigManage.GLOBAL_STRING_CONFIG.get("emailpasswd"));// 发件人地址和密码
              // **改为相应邮箱密码themail.sendout();
			return themail.sendout();
 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		//	throw new MyException(MyException.e, JavaEmail.class);
		}
 	}
}
