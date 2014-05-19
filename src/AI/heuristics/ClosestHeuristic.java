/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package AI.heuristics;


/**
 * A heuristic that uses the tile that is closest to the target
 * as the next best tile.
 */
public class ClosestHeuristic implements AStarHeuristic {

        public float getEstimatedDistanceToGoal(int startX, int startY, int goalX, int goalY) {         
                float dx = goalX - startX;
                float dy = goalY - startY;
                
                //float result = (float) (Math.sqrt((dx*dx)+(dy*dy)));
                
                //Optimization! Changed to distance^2 distance: (but looks more "ugly")
                
                float result = (float) (dx*dx)+(dy*dy);
                
                
                return result;
        }

}