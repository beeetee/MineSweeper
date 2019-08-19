import java.io.FileNotFoundException;
import java.util.ArrayList;
import mineSweeper.*;
public class minesweeper {

	private static ArrayList<Coordinate> list = new ArrayList<Coordinate> ();
	private static int[] xArray = {-1,-1,-1,0,0,1,1,1};
	private static int[] yArray = {1,0,-1,1,-1,1,0,-1};

	public static void main(String[] args) throws FileNotFoundException {		

		Oracle oracle = new Oracle(false);

		for(int n=0;n<5;n++){
			oracle.setMap("data/Board 5.txt");
			int size = oracle.getBoardSize();

			int x = (int)(Math.random()*size);
			int y = (int)(Math.random()*size);

			list = oracle.actionPerform(x, y, 0);
			if(!oracle.isGameOver())
				searchMine(oracle);

			while(!oracle.isGameOver()){
				while(!check(x,y)){
					x = (int)(Math.random()*size);
					y = (int)(Math.random()*size);
				}
				ArrayList<Coordinate> list2 = new ArrayList<Coordinate> ();
				list2 = oracle.actionPerform(x, y, 0);
				if(oracle.isGameOver())
					break;
				for(int i=0;i<list2.size();i++)
					list.add(list2.get(i));
				if(!oracle.isGameOver())
					searchMine(oracle);
			}
		}
		oracle.printScore();
		oracle.currentStatus();
	}

	private static void searchMine(Oracle oracle) {
		int newx;
		int newy;
		int size = oracle.getBoardSize();

		for(int i=0;i<list.size();i++){
			int cx = list.get(i).getX();
			int cy = list.get(i).getY();
			int[] jArray = new int[8];
			for(int j=0;j<8;j++)
				jArray[j]=-1;

			// 주변 지뢰가 1개인 경우
			if(list.get(i).getValue()==1){
				int count = 0;
				int countMine = 0;

				// 주변 좌표에 지뢰 유무 판단
				for(int j=0;j<8;j++){
					newx = cx+xArray[j];
					newy = cy+yArray[j];
					if(newx<0 || newx>=size || newy<0 || newy>=size)
						count++;
					else
						if(!check(newx,newy)){
							count++;
							jArray[j]=1;
							if(value(newx,newy)==99)
								countMine++;
						}
				}
				for(int j=0;j<8;j++){
					newx = cx+xArray[j];
					newy = cy+yArray[j];
				}
				// 주변에 지뢰가 1개 발견된 경우
				if(countMine>0){
					for(int j=0;j<8;j++){
						newx = cx+xArray[j];
						newy = cy+yArray[j];
						ArrayList<Coordinate> list2 = new ArrayList<Coordinate> ();
						if(check(newx,newy) &&(newx>=0&&newx<size)&&(newy>=0&&newy<size)){
							list2 = oracle.actionPerform(newx, newy, 0);
							if(oracle.isGameOver())
								break;
							if(list2!=null)
								for(int k=0;k<list2.size();k++)
									list.add(list2.get(k));								
						}
					}
				}

				// 주변에 확인된 지뢰는 없으나, 한군데 제외하고 지뢰가 없음을 알 떄
				else if(count==7){
					int j=0;
					for(;j<8;j++){
						if(jArray[j]<0)
							break;
					}						
					newx = cx+xArray[j];
					newy = cy+yArray[j];
					ArrayList<Coordinate> list2 = new ArrayList<Coordinate> ();
					if((newx>=0 && newx<size) && (newy>=0 && newy<size)){
						list2 = oracle.actionPerform(newx, newy, 1);
						if(oracle.isGameOver())
							break;
						if(list2!=null)
							for(int k=0;k<list2.size();k++)
								list.add(list2.get(k));	
					}
				}
			}

			// 주변에 지뢰가 2개 이상인 경우
			else if(list.get(i).getValue()>=2 && list.get(i).getValue()<8){
				int n = list.get(i).getValue();
				int countMine = 0;
				int count = 0;
				for(int j=0;j<8;j++)
					jArray[j]=-1;

				for(int j=0;j<8;j++){
					newx = cx+xArray[j];
					newy = cy+yArray[j];
					if(newx<0 || newx>=size || newy<0 || newy>=size)
						count++;
					else
						if(!check(newx,newy)){							
							if(value(newx,newy)==99)
								countMine++;
							else
								count++;
							jArray[j]=1;
						}
				}

				// 주변에 n개의 지뢰가 있을 때
				if(countMine>=n){
					for(int j=0;j<8;j++){
						newx = cx+xArray[j];
						newy = cy+yArray[j];
						ArrayList<Coordinate> list2 = new ArrayList<Coordinate> ();
						if(check(newx,newy) &&(newx>=0&&newx<size)&&(newy>=0&&newy<size)){
							list2 = oracle.actionPerform(newx, newy, 0);
							if(oracle.isGameOver())
								break;
							if(list2!=null)
								for(int k=0;k<list2.size();k++)
									list.add(list2.get(k));								
						}
					}
				}

				// 주변의 정보를 한군데 제외하고 알 때
				else if((count==8-n)&&(countMine==n-1)){
					int j=0;
					for(;j<8;j++){
						if(jArray[j]<0)
							break;
					}						
					newx = cx+xArray[j];
					newy = cy+yArray[j];
					ArrayList<Coordinate> list2 = new ArrayList<Coordinate> ();
					if((newx>=0 && newx<size) && (newy>=0 && newy<size)){
						list2 = oracle.actionPerform(newx, newy, 1);
						if(oracle.isGameOver())
							break;
						if(list2!=null)
							for(int k=0;k<list2.size();k++)
								list.add(list2.get(k));	
					}
				}
			}

			// 주변에 지뢰가 8개 있는 경우
			else if(list.get(i).getValue()==8){
				for(int j=0;j<8;j++){
					newx = cx+xArray[j];
					newy = cy+yArray[j];
					ArrayList<Coordinate> list2 = new ArrayList<Coordinate> ();
					if((newx>=0&&newx<size) && (newy>=0&&newy<size)){
						list2 = oracle.actionPerform(newx, newy, 1);
						if(oracle.isGameOver())
							break;
						for(int k=0;k<list2.size();k++)
							list.add(list2.get(k));	
					}
				}
			}
		}
	}

	private static boolean check(int newx, int newy) {
		for(int i=0;i<list.size();i++){
			if(newx==list.get(i).getX() && newy==list.get(i).getY())
				return false;
		}
		return true;
	}

	private static int value(int newx, int newy){
		for(int i=0;i<list.size();i++){
			if(newx==list.get(i).getX() && newy==list.get(i).getY())
				return list.get(i).getValue();
		}
		return -1;
	}
}