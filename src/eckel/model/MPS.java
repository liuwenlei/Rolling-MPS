package eckel.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import eckel.dao.ForecastDao;
import eckel.dao.MPSDao;
import eckel.dao.OrderDao;
import eckel.dao.ProductDao;

public class MPS {
	public int productid;
	public int ph;
	public int lotsize;
	public int rf;
	public int sf;
	public String planString;//plans的数字字符串表示
	public Plan[] plans;
	
	public static void update(int id){
		MPS mps=MPSDao.getMPSById(id);
		String targetDay;
		for(int i=1;i<=mps.ph;i++){
			targetDay=Day.getDay(i);
			mps.plans[i].odr=OrderDao.getSumByDate(mps.productid,targetDay);
			mps.plans[i].fc=ForecastDao.getSumByDate(mps.productid,targetDay);
		}
		mps.calculate();
		mps.stringPlans();
		MPSDao.ModifyMPSPlans(mps);
	}
	public void calculate(){
		Product product=ProductDao.getProductById(this.productid);
		this.plans[0].pab=product.stock;
		//计算毛需求量
		for(int i=1;i<=ph;i++){
			if(i<=rf){
				this.plans[i].gr=this.plans[i].odr;
			}else if(i>sf){
				this.plans[i].gr=this.plans[i].fc;
			}else{
				this.plans[i].gr=this.plans[i].fc>this.plans[i].odr?this.plans[i].fc:this.plans[i].odr;;
			}
		}	 			
		//计算pabI、pab、mps、nr
		for(int i=1;i<=ph;i++){
			this.plans[i].pabI=this.plans[i-1].pab-this.plans[i].gr;
			this.plans[i].mps=this.plans[i].pabI>product.safestock?0:((product.safestock-this.plans[i].pabI)/lotsize+1)*lotsize;
			this.plans[i].pab=this.plans[i].pabI+this.plans[i].mps;
			this.plans[i].nr=this.plans[i-1].pab-this.plans[i].gr>=product.safestock?0:this.plans[i].gr-this.plans[i-1].pab+product.safestock;
		}				
		//计算por
		for(int i=1;i<=ph-1;i++){
			this.plans[i].por=this.plans[i+product.leadtime].mps;
		}
		
		for(int i=1;i<=ph;i++){
			int todr=this.plans[i].odr;
			//计算todr
			for(int j=i+1;j<=ph;j++){
				if(this.plans[j].mps==0){
					todr+=this.plans[j].odr;
				}else{
					break;
				}
			}				
			//计算atp
			if(i==1){
				this.plans[1].atp=this.plans[0].pab-todr;
			}else if(this.plans[i].mps==0){
				this.plans[i].atp=0;
			}else if(i!=ph){
				this.plans[i].atp=this.plans[i].mps-todr;
			}else{
				this.plans[i].atp=0;
			}
		}							
	}
	public static void main(String[] args){
		MPS.update(3001);
	}
	public MPS(){
		
	}
	public MPS(int productid,int ph,int lotsize,int rf,int sf){
		this.productid=productid;
		this.ph=ph;
		this.lotsize=lotsize;
		this.rf=rf;
		this.sf=sf;
		initPlans();
		this.planString=stringPlans(this.plans);
	}
	
	public MPS(int productid,int ph,int lotsize,int rf,int sf,String planString){
		this.productid=productid;
		this.ph=ph;
		this.lotsize=lotsize;
		this.rf=rf;
		this.sf=sf;
		this.planString=planString;
		this.plans=parse(planString);
	}
	//新建MPS时初始化plans
	public void initPlans(){
		Product product=ProductDao.getProductById(this.productid);
		this.plans=new Plan[this.ph+1];
		for(int i=0;i<=this.ph;i++){
			this.plans[i]=new Plan(i);
		}
		this.plans[0].pab=product.stock;
	}
	
	public String getJson(){
		String s1="\"productid\":"+productid;
		String s2="\"ph\":"+ph;
		String s3="\"lotsize\":"+lotsize;
		String s4="\"rf\":"+rf;
		String s5="\"sf\":"+sf;
		String json="{"+s1+","+s2+","+s3+","+s4+","+s5+"}";
		return json;
	}
	//数字字符串化plans
	public String stringPlans(Plan[] plans){
		String text="";
		int i=1;
		for(Plan p:plans){
			if(i!=1){
				text+="&";
			}
			text+=p.getString();
			i++;
		}
		return text;
	}
	
	public String jsonPlans(){
		String text="[";
		int i=1;
		for(Plan p:this.plans){
			if(i!=1){
				text+=",";
			}
			text+=p.getJson();
			i++;
		}
		text+="]";
		return text;
	}
	
	public void stringPlans(){
		String text="";
		int i=1;
		for(Plan p:this.plans){
			if(i!=1){
				text+="&";
			}
			text+=p.getString();
			i++;
		}
		this.planString=text;
	}
	//解析数字字符串
	public Plan[] parse(String s){
		Plan[] plans=new Plan[ph+1];
		String[] onePlanStr=s.split("&");
		for(int i=0;i<=ph;i++){
			String[] planfield=onePlanStr[i].split(",");
			plans[i]=new Plan(planfield);
		}
		return plans;
	}

}
   
class Plan{
	public int ts;
	public int fc;
	public int odr;
	public int gr;
	public int pabI;
	public int pab;
	public int nr;
	public int mps;
	public int por;
	public int atp;
	
	public Plan(){
		
	}
	public Plan(int ts){
		this.ts=ts;
	}
	public Plan(int ts,int fc,int odr,int gr,int pabI,int pab,int nr,int mps,int por,int atp){
		this.ts=ts;
		this.fc=fc;
		this.odr=odr;
		this.pabI=pabI;
		this.pab=pab;
		this.nr=nr;
		this.mps=mps;
		this.por=por;
		this.atp=atp;
	}
	public Plan(String[] s){
		this.ts=Integer.parseInt(s[0]);
		this.fc=Integer.parseInt(s[1]);
		this.odr=Integer.parseInt(s[2]);
		this.gr=Integer.parseInt(s[3]);
		this.pabI=Integer.parseInt(s[4]);
		this.pab=Integer.parseInt(s[5]);
		this.nr=Integer.parseInt(s[6]);
		this.mps=Integer.parseInt(s[7]);
		this.por=Integer.parseInt(s[8]);
		this.atp=Integer.parseInt(s[9]);
	}
	
	public String getString(){
		String s=ts+","+fc+","+odr+","+gr+","+pabI+","+pab+","+nr+","+mps+","+por+","+atp;
		return s;
	}
	
	public String getJson(){
		String s1="\"ts\":"+ts;
		String s2="\"fc\":"+fc;
		String s3="\"odr\":"+odr;
		String s4="\"gr\":"+gr;
		String s5="\"pabI\":"+pabI;
		String s6="\"pab\":"+pab;
		String s7="\"nr\":"+nr;
		String s8="\"mps\":"+mps;
		String s9="\"por\":"+por;
		String s10="\"atp\":"+atp;
		String json="{"+s1+","+s2+","+s3+","+s4+","+s5+","+s6+","+s7+","+s8+","+s9+","+s10+"}";
		return json;
	}
}


//日期辅助类
class Day{
	public static String getDay(int i){//i为距今日的天数
		Date date=new Date();
		Calendar calendar=new GregorianCalendar();
		calendar.setTime(date); 
	    calendar.add(Calendar.DATE,i);
	    date=calendar.getTime();
		SimpleDateFormat format=new SimpleDateFormat("YYYY-MM-dd");
		String day=format.format(date);
		return day;
	}
}