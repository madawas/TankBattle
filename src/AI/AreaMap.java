/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package AI;

import java.util.ArrayList;

//import AI.utils.Logger;

public class AreaMap {

	private int mapWith;
	private int mapHeight;
	private ArrayList<ArrayList<Node>> map;
	private int startLocationX = 0;
	private int startLocationY = 0;
	private int goalLocationX = 0;
	private int goalLocationY = 0;
	public int[][] obstacleMap;

//	private Logger log = new Logger();
	
	public AreaMap(int mapWith, int mapHeight, int[][] obstacleMap) {
		this.mapWith = mapWith;
		this.mapHeight = mapHeight;
		this.obstacleMap = obstacleMap;
		createMap();
                registerEdges();
                
                
	}
	private void createMap() {
		Node node;
		map = new ArrayList<ArrayList<Node>>();
		for (int x=0; x<mapWith; x++) {
			map.add(new ArrayList<Node>());
			for (int y=0; y<mapHeight; y++) {
				node = new Node(x,y);
				if (obstacleMap[x][y] !=0)
					node.setObstical(true);
				map.get(x).add(node);
			}
		}
	}

	/**
	 * Registers the nodes edges (connections to its neighbors).
	 */
	private void registerEdges() {
		for ( int x = 0; x < mapWith-1; x++ ) {
			for ( int y = 0; y < mapHeight-1; y++ ) {
				Node node = map.get(x).get(y);
				if (!(y==0))
					node.setNorth(map.get(x).get(y-1));
				if (!(y==0) && !(x==mapWith))
					node.setNorthEast(map.get(x+1).get(y-1));
				if (!(x==mapWith))
					node.setEast(map.get(x+1).get(y));
				if (!(x==mapWith) && !(y==mapHeight))
					node.setSouthEast(map.get(x+1).get(y+1));
				if (!(y==mapHeight))
					node.setSouth(map.get(x).get(y+1));
				if (!(x==0) && !(y==mapHeight))
					node.setSouthWest(map.get(x-1).get(y+1));
				if (!(x==0))
					node.setWest(map.get(x-1).get(y));
				if (!(x==0) && !(y==0))
					node.setNorthWest(map.get(x-1).get(y-1));
			}
		}
	}
	
	

	public ArrayList<ArrayList<Node>> getNodes() {
		return map;
	}
	public void setObstical(int x, int y, boolean isObstical) {
		map.get(x).get(y).setObstical(isObstical);
	}

	public Node getNode(int x, int y) {
		return map.get(x).get(y);
	}

	public void setStartLocation(int x, int y) {
		map.get(startLocationX).get(startLocationY).setStart(false);
		map.get(x).get(y).setStart(true);
		startLocationX = x;
		startLocationY = y;
	}

	public void setGoalLocation(int x, int y) {
		map.get(goalLocationX).get(goalLocationY).setGoal(false);
		map.get(x).get(y).setGoal(true);
		goalLocationX = x;
		goalLocationY = y;
	}

	public int getStartLocationX() {
		return startLocationX;
	}

	public int getStartLocationY() {
		return startLocationY;
	}
	
	public Node getStartNode() {
		return map.get(startLocationX).get(startLocationY);
	}

	public int getGoalLocationX() {
		return goalLocationX;
	}

	public int getGoalLocationY() {
		return goalLocationY;
	}
	
	public Node getGoalLocation() {
		return map.get(goalLocationX).get(goalLocationY);
	}
	
	public float getDistanceBetween(Node node1, Node node2) {
		//if the nodes are on top or next to each other, return 1
		if (node1.getX() == node2.getX() || node1.getY() == node2.getY()){
			return 1*(mapHeight+mapWith);
		} else { //if they are diagonal to each other return diagonal distance: sqrt(1^2+1^2)
			return (float) 1.7*(mapHeight+mapWith);
		}
	}
	
	public int getMapWith() {
		return mapWith;
	}
	public int getMapHeight() {
		return mapHeight;
	}
	public void clear() {
		startLocationX = 0;
		startLocationY = 0;
		goalLocationX = 0;
		goalLocationY = 0;
		createMap();
		registerEdges();
	}
}