package tempfinalhw;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;



public class movie {

	class person{
		LinkedList<Integer> friends=new LinkedList<Integer>();//해당배우가 알고있는 배우 목록
		//알고있는 사람목록에 후에 본인이 추가 되므로 삭제한다. 따라서 삭제가 용이한 LinkedList로 자료형을 정했다. 
	}
	
	ArrayList<person> Actor=new ArrayList<person>();//배우목록
	static ArrayList<String> actorlist=new ArrayList<String>();//크기가 정해져있지 않으므로 arrayList
	public static void main(String[] args) {
		movie Movies=new movie();
		try {
			BufferedReader in=new BufferedReader(new FileReader("Movies.txt"));
			String s;
			while((s=in.readLine())!=null) {
				//System.out.println(s);
				Movies.putActor(s);
			}
			in.close();
		}catch(IOException e) {
			System.err.println(e);
			System.exit(1);
		}
		System.out.println("Verdonck, Thomas 의 degree");
		System.out.println(Movies.degree("Verdonck, Thomas"));
		System.out.println("Flowers, Bess 의 degree");
		System.out.println(Movies.degree("Flowers, Bess"));
		System.out.println("Dane, Eric 의 degree");
		System.out.println(Movies.degree("Dane, Eric"));
		System.out.println("가장 큰 degree를 가지는 배우");
		Movies.maxDegree();
		System.out.println("배우들 degree의 평균");
		Movies.avgDegree();
	}
	
	private  void putActor(String actor) {
		
		//actorlist에서의 주소와 Actor의 배열주소는 가리키는 대상이 같다. 
		String[] object=actor.split("/");
		for(int i=1;i<object.length;i++) {
			if(actorlist.isEmpty()||actorlist.contains(object[i])==false) {//actorlist가 비어있거나 actorlist에서 못찾으면
				Actor.add(new person());//actor만들기
				actorlist.add(object[i]);//actor목록에 추가하기
			}
			//이미 있으면 그냥 다음으로 넘어감
		}
		ArrayList <Integer>objectidx=new ArrayList<Integer>();//새로 받은 actor를 숫자로 바꾸기
		for(int i=1;i<object.length;i++) {
			objectidx.add(findingraph(object[i]));
		}
		for(int i=1;i<object.length;i++) {
			Actor.get(findingraph(object[i])).friends.addAll(objectidx);//읻단 해당 actor가 같이 출연한 배우를 모두 friends배열에 넣기(본인 포함)
			HashSet<Integer> distinctactor=new HashSet<Integer>(Actor.get(findingraph(object[i])).friends);//중복된 부분 제거
			Actor.get(findingraph(object[i])).friends=new LinkedList<Integer>(distinctactor);
		}
		for(int i=0;i<objectidx.size();i++) {//본인도 추가 되었으므로 삭제 해야함 
			Actor.get(objectidx.get(i)).friends.remove(objectidx.get(i));
		}
	}
	private int findingraph(String actor) {//해당 배우가 몇번 index 에 있는지 찾는 함수
		
		if(actorlist.contains(actor)) {//있으면 해당 actor의 index
			return actorlist.indexOf(actor);
		}
		else {//없으면 size return
			return actorlist.size();//찾지 못하면 list크기 return
		}
	}
	private int degree(String name) {
		if(actorlist.contains(name)) {
	     int idx=actorlist.indexOf(name);
	     return Actor.get(idx).friends.size();}//해당 배우의 degree=friends.size()
		else
			return 0;//해당 배우가 없으면 0 출력
	}

	private void maxDegree() {
		int tempmax=0;
		int actoridx=0;
		for(int i=0;i<Actor.size();i++) {
			if(Actor.get(i).friends.size()>tempmax) {
				tempmax=Actor.get(i).friends.size();//최대값 저장
				actoridx=i;//최대값인 actor의 index저장
			}
		}
		System.out.println(actorlist.get(actoridx));
	}
	private void avgDegree() {
		int sum=0;
		float avg=0;
		for(int i=0;i<Actor.size();i++) {
			sum=sum+Actor.get(i).friends.size();//모든 degree의 합
		}
		avg=sum/(float)Actor.size();//형변환
		System.out.println(avg);
	}
}
