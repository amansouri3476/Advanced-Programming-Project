package GameObjects;

import Lists.ListOfEnemyGroups;
import Movers.EnemyGroupMover;
import Screen.GamePlayScrolling.Background;
import Screen.GamePlayScrolling.Scroll;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class EnemyGroup extends coordinatedObject{
    public String type;
    public int numberAlive;
    public CopyOnWriteArrayList<Enemy> ListOfFighters = new CopyOnWriteArrayList<>();
    public CopyOnWriteArrayList<Enemy> innerCircleFightersI = new CopyOnWriteArrayList<>();
    public CopyOnWriteArrayList<Enemy> innerCircleFightersII = new CopyOnWriteArrayList<>();
    public EnemyGroupMover enemyGroupMover;

    //////////////// for circular and rotational movement ////////////////
    public int radius;
    public int radius_2;
    public int radius_3;
    public int center_x;
    public int center_y;
    public double theta;
    public double theta_2;
    public double theta_3;
    public double theta_o=0;
    public double theta_o_2=0;
    public double theta_o_3=0;
    public int rotateTimer=0;
    public int changeCenterTimer=0;
    private int previousNumber;
    private int previousNumberInnerCircleI;
    private int previousNumberInnerCircleII;
    //////////////////////////////////////////////////////////////////////

    public EnemyGroup(String type, int topLeftCorner_x, int topLeftCorner_y, String movement, int number){
        this.type = type;
        this.numberAlive = number;
        this.x_coordinate = topLeftCorner_x;
        this.y_coordinate = topLeftCorner_y;
        this.enemyGroupMover = new EnemyGroupMover(1, movement, 600);
        ListOfEnemyGroups.EnemyGroups.add(this);

        if (type.equals("Rectangular")){
            if (number >= 40){
                int row=0;
                int remaining=9;
                int left=number;
                int i=1;
                while (i <= remaining){
                    if (row % 2 == 1){
                        ListOfFighters.add(new Enemy(topLeftCorner_x + (remaining -(i-1) - 1)*50, -100 - row * 70, "fighter", movement, (remaining -(i-1) - 1)*50, - row * 70));
                        i++;
                    }
                    else {
                        ListOfFighters.add(new Enemy(topLeftCorner_x + (i-1)*50, -100 - row * 70, "fighter", movement, (i-1)*50, - row * 70));
                        i++;
                    }

                    if (i == 10){
                        row++;
                        left = left - 9;
                        if (left > 0){
                            i=1;
                        }
                        if (left < 9){
                            remaining = left;
                        }
                    }
                }
            }
            if (number >= 30 && number < 40){
                int row=0;
                int remaining=8;
                int left=number;
                int i=1;
                while (i <= remaining){
                    if (row % 2 == 1){
                        ListOfFighters.add(new Enemy(topLeftCorner_x + (remaining -(i-1) - 1)*50, -100 - row * 70, "fighter", movement, (remaining -(i-1) - 1)*50, - row * 70));
                        i++;
                    }
                    else {
                        ListOfFighters.add(new Enemy(topLeftCorner_x + (i-1)*50, -100 - row * 70, "fighter", movement, (i-1)*50, - row * 70));
                        i++;
                    }
                    if (i == 9){
                        row++;
                        left = left - 8;
                        if (left > 0){
                            i=1;
                        }
                        if (left < 8){
                            remaining = left;
                        }
                    }
                }
            }
            if (number < 30){
                int row=0;
                int remaining=7;
                int left=number;
                int i=1;
                while (i <= remaining){
                    if (row % 2 == 1){
                        ListOfFighters.add(new Enemy(topLeftCorner_x + (remaining -(i-1) - 1)*50, -100 - row * 70, "fighter", movement, (remaining -(i-1) - 1)*50, - row * 70));
                        i++;
                    }
                    else {
                        ListOfFighters.add(new Enemy(topLeftCorner_x + (i-1)*50, -100 - row * 70, "fighter", movement, (i-1)*50, - row * 70));
                        i++;
                    }
                    if (i == 8){
                        row++;
                        left = left - 7;
                        if (left > 0){
                            i=1;
                        }
                        if (left < 7){
                            remaining = left;
                        }
                    }
                }
            }
        }

        if (type.equals("Rotational")){

        }
        if (type.equals("Suicidal")){

        }
    }
    public EnemyGroup(String type, String movement, int x_center, int y_center, int number) {
        this.type = type;
        this.numberAlive = number;
        this.center_x = x_center;
        this.center_y = y_center;
        this.x_coordinate = x_center;
        this.y_coordinate = y_center;
        this.enemyGroupMover = new EnemyGroupMover(1, movement, 400);
        ListOfEnemyGroups.EnemyGroups.add(this);

        if (type.equals("Circular")){

            if (number < 30){
                this.radius = (int)(number * (50.0d)/(2.0d * 3.14d));
                this.theta = 2d * 3.14d/((double) number);
                this.previousNumber = number;

                int i = 1;
                while (i <= number){
                    ListOfFighters.add(new Enemy(x_coordinate + (int)(radius * Math.cos((i-1)*theta)),
                            y_coordinate + (int)(radius * Math.sin((i-1)*theta)), "fighter",
                            "Fixed", (int)(radius * Math.cos((i-1)*theta)),
                            (int)(radius * Math.sin((i-1)*theta))));
                    i++;
                }
            }
            if (number >= 30 && number < 40){
                this.radius = (int)(30 * (50.0d)/(2.0d * 3.14d));
                this.theta = 2d * 3.14d/((double) 30);
                this.previousNumber = 30;
                this.previousNumberInnerCircleI = number - 30;

                this.radius_2 = (int)((number - 30) * (50.0d)/(2.0d * 3.14d));
                this.theta_2 = 2d * 3.14d/((double) (number - 30));

                int i = 1;
                while (i <= number){
                    if (i <= 30){
                        ListOfFighters.add(new Enemy(x_coordinate + (int)(radius * Math.cos((i-1)*theta)),
                                y_coordinate + (int)(radius * Math.sin((i-1)*theta)), "fighter",
                                "Fixed", (int)(radius * Math.cos((i-1)*theta)),
                                (int)(radius * Math.sin((i-1)*theta))));
                        i++;
                    }
                    else {
                        innerCircleFightersI.add(new Enemy(x_coordinate + (int)(radius_2 * Math.cos((i-1)*theta_2)),
                                y_coordinate + (int)(radius_2 * Math.sin((i-1)*theta_2)), "fighter",
                                "Fixed", (int)(radius_2 * Math.cos((i-1)*theta_2)),
                                (int)(radius_2 * Math.sin((i-1)*theta_2))));
                        i++;
                    }
                }
            }
            if (number >=40){
                this.radius = (int)(30 * (50.0d)/(2.0d * 3.14d));
                this.theta = 2d * 3.14d/((double) 30);

                this.previousNumber = 30;
                this.previousNumberInnerCircleI = 10;
                this.previousNumberInnerCircleII = number - 40;

                this.radius_2 = (int)(10 * (50.0d)/(2.0d * 3.14d));
                this.theta_2 = 2d * 3.14d/((double) 10);

                this.radius_3 = (int)(40 * (50.0d)/(2.0d * 3.14d));
                this.theta_3 = 2d * 3.14d/((double) (number - 40));

                int i = 1;
                while (i <= number){
                    if (i <= 30){
                        ListOfFighters.add(new Enemy(x_coordinate + (int)(radius * Math.cos((i-1)*theta)),
                                y_coordinate + (int)(radius * Math.sin((i-1)*theta)), "fighter",
                                "Fixed", (int)(radius * Math.cos((i-1)*theta)),
                                (int)(radius * Math.sin((i-1)*theta))));
                        i++;
                    }
                    if (i > 30 && i <= 40){
                        innerCircleFightersI.add(new Enemy(x_coordinate + (int)(radius_2 * Math.cos((i-1)*theta_2)),
                                y_coordinate + (int)(radius_2 * Math.sin((i-1)*theta_2)), "fighter",
                                "Fixed", (int)(radius_2 * Math.cos((i-1)*theta_2)),
                                (int)(radius_2 * Math.sin((i-1)*theta_2))));
                        i++;
                    }
                    if (i > 40 && i <= number) {
                        innerCircleFightersII.add(new Enemy(x_coordinate + (int)(radius_3 * Math.cos((i-1)*theta_3)),
                                y_coordinate + (int)(radius_3 * Math.sin((i-1)*theta_3)), "fighter",
                                "Fixed", (int)(radius_3 * Math.cos((i-1)*theta_3)),
                                (int)(radius_3 * Math.sin((i-1)*theta_3))));
                        i++;
                    }
                }
            }

        }
    }

    public EnemyGroup(String type, int center_x, int center_y, int safeZoneRadii, int number) {
        this.type = type;
        this.numberAlive = number;
        this.center_x = center_x;
        this.center_y = center_y;
        this.x_coordinate = center_x;
        this.destination_x = center_x;
        this.y_coordinate = center_y;
        this.destination_y = center_y;
        this.enemyGroupMover = new EnemyGroupMover("circular_fix");
        ListOfEnemyGroups.EnemyGroups.add(this);

        int level_I = 30;
        int level_II = 70;
        int level_III = 100;

        ///// Showing Safe Zone /////
        Background.showSafeZone = true;
        Background.SZcenterX = center_x;
        Background.SZcenterY = center_y;
        Background.SZcenterRadii = safeZoneRadii;

        if (type.equals("Rotational")){
            int i = 1;
            while (i < number){
                if (i <= level_I){
                    this.radius = safeZoneRadii;
                    this.theta = 2d * 3.14d/((double) number);
                    this.previousNumber = number;

                    ListOfFighters.add(new Enemy(x_coordinate + 4 * (int)(radius * Math.cos((i-1)*theta)),
                            y_coordinate + 4 * (int)(radius * Math.sin((i-1)*theta)), "fighter",
                            "Fixed", (int)(radius * Math.cos((i-1)*theta)),
                            (int)(radius * Math.sin((i-1)*theta))));
                    ListOfFighters.get(i-1).enemyMover.destinationActive_x = true;
                    ListOfFighters.get(i-1).enemyMover.destinationActive_y = true;
                    ListOfFighters.get(i-1).destination_x = x_coordinate +  (int)(radius * Math.cos((i-1)*theta));
                    ListOfFighters.get(i-1).destination_y = y_coordinate +  (int)(radius * Math.sin((i-1)*theta));
                    i++;
                    }
                if (i > level_I && i <= level_II){

                    this.radius_2 = safeZoneRadii + 100;
                    this.theta_2 = 2d * 3.14d/((double) (number - level_I));
                    this.previousNumberInnerCircleI = (number - level_I);

                    innerCircleFightersI.add(new Enemy(x_coordinate + 5 * (int)(radius_2 * Math.cos((i-1)*theta_2)),
                            y_coordinate + 5 * (int)(radius_2 * Math.sin((i-1)*theta_2)), "fighter",
                            "Fixed", (int)(radius_2 * Math.cos((i-1)*theta_2)),
                            (int)(radius_2 * Math.sin((i-1)*theta_2))));
                    innerCircleFightersI.get(i - level_I -1).enemyMover.destinationActive_x = true;
                    innerCircleFightersI.get(i - level_I -1).enemyMover.destinationActive_y = true;
                    innerCircleFightersI.get(i - level_I -1).destination_x = x_coordinate +  (int)(radius_2 * Math.cos((i-1)*theta_2));
                    innerCircleFightersI.get(i - level_I -1).destination_y = y_coordinate +  (int)(radius_2 * Math.sin((i-1)*theta_2));
                    i++;
                }
                if (i > level_II && i <= level_III){

                    this.radius_3 = safeZoneRadii + 200;
                    this.theta_3 = 2d * 3.14d/((double) (number - level_II));
                    this.previousNumberInnerCircleI = (number - level_II);

                    innerCircleFightersII.add(new Enemy(x_coordinate + 6 * (int)(radius_3 * Math.cos((i-1)*theta_3)),
                            y_coordinate + 6 * (int)(radius_3 * Math.sin((i-1)*theta_3)), "fighter",
                            "Fixed", (int)(radius_3 * Math.cos((i-1)*theta_3)),
                            (int)(radius_3 * Math.sin((i-1)*theta_3))));
                    innerCircleFightersII.get(i - level_II -1).enemyMover.destinationActive_x = true;
                    innerCircleFightersII.get(i - level_II -1).enemyMover.destinationActive_y = true;
                    innerCircleFightersII.get(i - level_II -1).destination_x = x_coordinate +  (int)(radius_3 * Math.cos((i-1)*theta_3));
                    innerCircleFightersII.get(i - level_II -1).destination_y = y_coordinate +  (int)(radius_3 * Math.sin((i-1)*theta_3));
                    i++;
                }

            }

        }
    }

    public void updateConfiguration() {
        if (type.equals("Rectangular")){
            updateConfigurationRectangular();
        }
        if (type.equals("Circular")){
            updateConfigurationCircular();
        }
        if (type.equals("Rotational")){
            updateConfigurationRotational();
        }
        if (type.equals("Suicidal")){
            updateConfigurationSuicidal();
        }

    }

    private void updateConfigurationRectangular() {
        if (ListOfFighters.size() < 30){
            AtomicInteger enemyCounter = new AtomicInteger(0);
            for (int i = 1; i< ListOfFighters.size(); i++) {
                if ((i/8) % 2 == 1){
                    ListOfFighters.get(i).inGroupX = (7 - i%8)*50;
                }
                else {
                    ListOfFighters.get(i).inGroupX = (i%8)*50;
                }

                ListOfFighters.get(i).inGroupY = -(i/8)*70;
                enemyCounter.getAndIncrement();
            }
        }
        if (ListOfFighters.size() < 40 && ListOfFighters.size() >= 30){
            AtomicInteger enemyCounter = new AtomicInteger(0);
            for (int i = 1; i< ListOfFighters.size(); i++) {
                if ((i/9) % 2 == 1){
                    ListOfFighters.get(i).inGroupX = (8 - i%9)*50;
                }
                else {
                    ListOfFighters.get(i).inGroupX = (i%9)*50;
                }

                ListOfFighters.get(i).inGroupY = -(i/9)*70;
                enemyCounter.getAndIncrement();
            }
        }
        if (ListOfFighters.size() >= 40){
            AtomicInteger enemyCounter = new AtomicInteger(0);
            for (int i = 1; i< ListOfFighters.size(); i++) {
                if ((i/10) % 2 == 1){
                    ListOfFighters.get(i).inGroupX = (9 - i%10)*50;
                }
                else {
                    ListOfFighters.get(i).inGroupX = (i%10)*50;
                }

                ListOfFighters.get(i).inGroupY = -(i/10)*70;
                enemyCounter.getAndIncrement();
            }
        }
        for (Enemy enemy: ListOfFighters){
            enemy.destination_x = this.x_coordinate + enemy.inGroupX;
            enemy.destination_y = this.y_coordinate + enemy.inGroupY;
        }
    }

    private void updateConfigurationCircular() {
        if (changeCenterTimer == 4000){
            int x_new = 700 + (int) (800 * (Math.random()-0.5));
            int y_new = 200 + (int) (600 * (Math.random()-0.5));
            this.destination_x = x_new;
            this.destination_y = y_new;
            enemyGroupMover.destinationActive_x = true;
            enemyGroupMover.destinationActive_y = true;
        }
        ///// if there's a need to update the in_group positions due to a number change /////
        if (ListOfFighters.size() != previousNumber) {

            //// when all have changed position we need to get back to rotating state ////
            int finishCounter = 0;
            for (Enemy enemy : ListOfFighters) {
                if (!enemy.enemyMover.destinationActive_x && !enemy.enemyMover.destinationActive_y) {
                    finishCounter++;
                }
            }
            if (finishCounter == ListOfFighters.size()) {
                previousNumber = ListOfFighters.size();
            }

            /////////// now we need to activate destination_x,y to change enemies' positions smoothly ///////////
            for (Enemy enemy : ListOfFighters) {
                enemy.enemyMover.destinationActive_x = true;
                enemy.enemyMover.destinationActive_y = true;
            }

            ////////// preventing outer circle from interfering with the inner circle //////////
            if (innerCircleFightersI.size() != 0 && ListOfFighters.size() <= 15){
                radius = (int) (15 * (50.0d) / (2.0d * 3.14d));
                theta = 2d * 3.14d / ((double) ListOfFighters.size());
            }
            if (innerCircleFightersI.size() == 0){
                radius = (int) (ListOfFighters.size() * (50.0d) / (2.0d * 3.14d));
                theta = 2d * 3.14d / ((double) ListOfFighters.size());
            }
            if (innerCircleFightersI.size() != 0 && ListOfFighters.size() > 15){
                radius = (int) (ListOfFighters.size() * (50.0d) / (2.0d * 3.14d));
                theta = 2d * 3.14d / ((double) ListOfFighters.size());
            }

            for (int i = 1; i <= ListOfFighters.size(); i++) {
                ListOfFighters.get(i - 1).inGroupX = (int) (radius * Math.cos((i - 1) * theta + theta_o));
                ListOfFighters.get(i - 1).inGroupY = (int) (radius * Math.sin((i - 1) * theta + theta_o));
            }
            for (Enemy enemy: ListOfFighters){
                enemy.destination_x = this.x_coordinate + enemy.inGroupX;
                enemy.destination_y = this.y_coordinate + enemy.inGroupY;
            }
        }
        else rotateOuterCircle();
        if (innerCircleFightersI.size() != previousNumberInnerCircleI) {
            //// when all have changed position we need to get back to rotating state ////
            int finishCounter = 0;
            for (Enemy enemy : innerCircleFightersI) {
                if (!enemy.enemyMover.destinationActive_x && !enemy.enemyMover.destinationActive_y) {
                    finishCounter++;
                }
            }
            if (finishCounter == innerCircleFightersI.size()) {
                previousNumberInnerCircleI = innerCircleFightersI.size();
            }
            /////////// now we need to activate destination_x,y to change enemies' positions smoothly ///////////
            for (Enemy enemy : innerCircleFightersI) {
                enemy.enemyMover.destinationActive_x = true;
                enemy.enemyMover.destinationActive_y = true;
            }

            radius_2 = (int)((innerCircleFightersI.size()) * (50.0d)/(2.0d * 3.14d));
            theta_2 = 2d * 3.14d/((double) innerCircleFightersI.size());

            for (int i = 1; i<= (innerCircleFightersI.size()); i++) {
                innerCircleFightersI.get(i-1).inGroupX = (int)(radius_2 * Math.cos((i-1)*theta_2 + theta_o_2));
                innerCircleFightersI.get(i-1).inGroupY = (int)(radius_2 * Math.sin((i-1)*theta_2 + theta_o_2));
            }
            for (Enemy enemy: innerCircleFightersI){
                enemy.destination_x = this.x_coordinate + enemy.inGroupX;
                enemy.destination_y = this.y_coordinate + enemy.inGroupY;
            }
        }
        else rotateInnerCircleI();
        if (innerCircleFightersII.size() != previousNumberInnerCircleII) {
            //// when all have changed position we need to get back to rotating state ////
            int finishCounter = 0;
            for (Enemy enemy : innerCircleFightersII) {
                if (!enemy.enemyMover.destinationActive_x && !enemy.enemyMover.destinationActive_y) {
                    finishCounter++;
                }
            }
            if (finishCounter == innerCircleFightersII.size()) {
                previousNumberInnerCircleII = innerCircleFightersII.size();
            }
            /////////// now we need to activate destination_x,y to change enemies' positions smoothly ///////////
            for (Enemy enemy : innerCircleFightersII) {
                enemy.enemyMover.destinationActive_x = true;
                enemy.enemyMover.destinationActive_y = true;
            }

            radius_3 = (int)(40 * (50.0d)/(2.0d * 3.14d));
            theta_3 = 2d * 3.14d/((double) innerCircleFightersII.size());

            for (int i = 1; i<= (innerCircleFightersII.size()); i++) {
                innerCircleFightersII.get(i-1).inGroupX = (int)(radius_3 * Math.cos((i-1)*theta_3 + theta_o_3));
                innerCircleFightersII.get(i-1).inGroupY = (int)(radius_3 * Math.sin((i-1)*theta_3 + theta_o_3));
            }
            for (Enemy enemy: innerCircleFightersII){
                enemy.destination_x = this.x_coordinate + enemy.inGroupX;
                enemy.destination_y = this.y_coordinate + enemy.inGroupY;
            }
        }
        else rotateInnerCircleII();
    }

    private void updateConfigurationRotational() {

        ///// if there's a need to update the in_group positions due to a number change /////
        if (ListOfFighters.size() != previousNumber) {

            //// when all have changed position we need to get back to rotating state ////
            int finishCounter = 0;
            for (Enemy enemy : ListOfFighters) {
                if (!enemy.enemyMover.destinationActive_x && !enemy.enemyMover.destinationActive_y) {
                    finishCounter++;
                }
            }
            if (finishCounter == ListOfFighters.size()) {
                previousNumber = ListOfFighters.size();
            }

            /////////// now we need to activate destination_x,y to change enemies' positions smoothly ///////////
            for (Enemy enemy : ListOfFighters) {
                enemy.enemyMover.destinationActive_x = true;
                enemy.enemyMover.destinationActive_y = true;
            }

            ////////// preventing outer circle from interfering with the inner circle //////////

            theta = 2d * 3.14d / ((double) ListOfFighters.size());

            for (int i = 1; i <= ListOfFighters.size(); i++) {
                ListOfFighters.get(i - 1).inGroupX = (int) (radius * Math.cos((i - 1) * theta + theta_o));
                ListOfFighters.get(i - 1).inGroupY = (int) (radius * Math.sin((i - 1) * theta + theta_o));
            }
            for (Enemy enemy: ListOfFighters){
                enemy.destination_x = this.x_coordinate + enemy.inGroupX;
                enemy.destination_y = this.y_coordinate + enemy.inGroupY;
            }
        }
        else rotateOuterCircleRotational();
        if (innerCircleFightersI.size() != previousNumberInnerCircleI) {
            //// when all have changed position we need to get back to rotating state ////
            int finishCounter = 0;
            for (Enemy enemy : innerCircleFightersI) {
                if (!enemy.enemyMover.destinationActive_x && !enemy.enemyMover.destinationActive_y) {
                    finishCounter++;
                }
            }
            if (finishCounter == innerCircleFightersI.size()) {
                previousNumberInnerCircleI = innerCircleFightersI.size();
            }
            /////////// now we need to activate destination_x,y to change enemies' positions smoothly ///////////
            for (Enemy enemy : innerCircleFightersI) {
                enemy.enemyMover.destinationActive_x = true;
                enemy.enemyMover.destinationActive_y = true;
            }

            theta_2 = 2d * 3.14d/((double) innerCircleFightersI.size());

            for (int i = 1; i<= (innerCircleFightersI.size()); i++) {
                innerCircleFightersI.get(i-1).inGroupX = (int)(radius_2 * Math.cos((i-1)*theta_2 + theta_o_2));
                innerCircleFightersI.get(i-1).inGroupY = (int)(radius_2 * Math.sin((i-1)*theta_2 + theta_o_2));
            }
            for (Enemy enemy: innerCircleFightersI){
                enemy.destination_x = this.x_coordinate + enemy.inGroupX;
                enemy.destination_y = this.y_coordinate + enemy.inGroupY;
            }
        }
        else rotateInnerCircleIRotational();
        if (innerCircleFightersII.size() != previousNumberInnerCircleII) {
            //// when all have changed position we need to get back to rotating state ////
            int finishCounter = 0;
            for (Enemy enemy : innerCircleFightersII) {
                if (!enemy.enemyMover.destinationActive_x && !enemy.enemyMover.destinationActive_y) {
                    finishCounter++;
                }
            }
            if (finishCounter == innerCircleFightersII.size()) {
                previousNumberInnerCircleII = innerCircleFightersII.size();
            }
            /////////// now we need to activate destination_x,y to change enemies' positions smoothly ///////////
            for (Enemy enemy : innerCircleFightersII) {
                enemy.enemyMover.destinationActive_x = true;
                enemy.enemyMover.destinationActive_y = true;
            }

            theta_3 = 2d * 3.14d/((double) innerCircleFightersII.size());

            for (int i = 1; i<= (innerCircleFightersII.size()); i++) {
                innerCircleFightersII.get(i-1).inGroupX = (int)(radius_3 * Math.cos((i-1)*theta_3 + theta_o_3));
                innerCircleFightersII.get(i-1).inGroupY = (int)(radius_3 * Math.sin((i-1)*theta_3 + theta_o_3));
            }
            for (Enemy enemy: innerCircleFightersII){
                enemy.destination_x = this.x_coordinate + enemy.inGroupX;
                enemy.destination_y = this.y_coordinate + enemy.inGroupY;
            }
        }
        else rotateInnerCircleIIRotational();
    }

    private void updateConfigurationSuicidal() {
    }

    private void rotateOuterCircleRotational() {
        boolean changeFinished = changeTerminationCheck(ListOfFighters);

        if (changeFinished){
            if (rotateTimer == Scroll.rotateTime) {
                theta_o += 0.02;
            }
            for (int i = 1; i<= ListOfFighters.size(); i++) {
                ListOfFighters.get(i-1).inGroupX = (int)(radius * Math.cos((i-1)*theta + theta_o));
                ListOfFighters.get(i-1).inGroupY = (int)(radius * Math.sin((i-1)*theta + theta_o));
            }

            for (Enemy enemy: ListOfFighters){
                enemy.enemyMover.destinationActive_x = true;
                enemy.enemyMover.destinationActive_y = true;
                enemy.destination_x = this.x_coordinate + enemy.inGroupX;
                enemy.destination_y = this.y_coordinate + enemy.inGroupY;
            }
        }
    }

    private void rotateInnerCircleIRotational() {
        boolean changeFinished = changeTerminationCheck(innerCircleFightersI);

        if (changeFinished){
            if (rotateTimer == Scroll.rotateTime) {
                theta_o_2 += 0.02;
            }
            for (int i = 1; i<= innerCircleFightersI.size(); i++) {
                innerCircleFightersI.get(i-1).inGroupX = (int)(radius_2 * Math.cos((i-1)*theta_2 + theta_o_2));
                innerCircleFightersI.get(i-1).inGroupY = (int)(radius_2 * Math.sin((i-1)*theta_2 + theta_o_2));
            }

            for (Enemy enemy: innerCircleFightersI){
                enemy.enemyMover.destinationActive_x = true;
                enemy.enemyMover.destinationActive_y = true;
                enemy.destination_x = this.x_coordinate + enemy.inGroupX;
                enemy.destination_y = this.y_coordinate + enemy.inGroupY;
            }
        }
    }

    private void rotateInnerCircleIIRotational() {
        boolean changeFinished = changeTerminationCheck(innerCircleFightersII);

        if (changeFinished){
            if (rotateTimer == Scroll.rotateTime) {
                theta_o_3 += 0.02;
            }
            for (int i = 1; i<= innerCircleFightersII.size(); i++) {
                innerCircleFightersII.get(i-1).inGroupX = (int)(radius_3 * Math.cos((i-1)*theta_3 + theta_o_3));
                innerCircleFightersII.get(i-1).inGroupY = (int)(radius_3 * Math.sin((i-1)*theta_3 + theta_o_3));
            }

            for (Enemy enemy: innerCircleFightersII){
                enemy.enemyMover.destinationActive_x = true;
                enemy.enemyMover.destinationActive_y = true;
                enemy.destination_x = this.x_coordinate + enemy.inGroupX;
                enemy.destination_y = this.y_coordinate + enemy.inGroupY;
            }
        }
    }

    private void rotateOuterCircle() {

        if (rotateTimer == Scroll.rotateTime) {
            theta_o += 0.02;
        }
        for (int i = 1; i<= ListOfFighters.size(); i++) {
            ListOfFighters.get(i-1).inGroupX = (int)(radius * Math.cos((i-1)*theta + theta_o));
            ListOfFighters.get(i-1).inGroupY = (int)(radius * Math.sin((i-1)*theta + theta_o));
        }
        for (Enemy enemy: ListOfFighters){
            enemy.x_coordinate = this.x_coordinate + enemy.inGroupX;
            enemy.y_coordinate = this.y_coordinate + enemy.inGroupY;
        }
    }

    private void rotateInnerCircleI() {
        if (rotateTimer == Scroll.rotateTime) {
            theta_o_2 += 0.02;
        }

        for (int i = 1; i<= (innerCircleFightersI.size()); i++) {
            innerCircleFightersI.get(i-1).inGroupX = (int)(radius_2 * Math.cos((i-1)*theta_2 + theta_o_2));
            innerCircleFightersI.get(i-1).inGroupY = (int)(radius_2 * Math.sin((i-1)*theta_2 + theta_o_2));
        }
        for (Enemy enemy: innerCircleFightersI){
            enemy.x_coordinate = this.x_coordinate + enemy.inGroupX;
            enemy.y_coordinate = this.y_coordinate + enemy.inGroupY;
        }
    }

    private void rotateInnerCircleII() {
        if (rotateTimer == Scroll.rotateTime) {
            theta_o_3 += 0.05;
        }

        for (int i = 1; i<= (innerCircleFightersII.size()); i++) {
            innerCircleFightersII.get(i-1).inGroupX = (int)(radius_3 * Math.cos((i-1)*theta_3 + theta_o_3));
            innerCircleFightersII.get(i-1).inGroupY = (int)(radius_3 * Math.sin((i-1)*theta_3 + theta_o_3));
        }
        for (Enemy enemy: innerCircleFightersII){
            enemy.x_coordinate = this.x_coordinate + enemy.inGroupX;
            enemy.y_coordinate = this.y_coordinate + enemy.inGroupY;
        }
    }

    private boolean changeTerminationCheck(CopyOnWriteArrayList<Enemy> listOfFighters) {
        int finishCounter = 0;
        for (Enemy enemy : listOfFighters) {
            if (!enemy.enemyMover.destinationActive_x && !enemy.enemyMover.destinationActive_y) {
                finishCounter++;
            }
        }
        return finishCounter == listOfFighters.size();
    }


    private void rotate() {
        if (ListOfFighters.size() < 30){
            radius = (int)(ListOfFighters.size() * (50.0d)/(2.0d * 3.14d));
            theta = 2d * 3.14d/((double) ListOfFighters.size());

            if (rotateTimer == Scroll.rotateTime) {
                theta_o += 0.02;
            }
            AtomicInteger enemyCounter = new AtomicInteger(0);
            for (int i = 1; i<= ListOfFighters.size(); i++) {
                ListOfFighters.get(i-1).inGroupX = (int)(radius * Math.cos((i-1)*theta + theta_o));
                ListOfFighters.get(i-1).inGroupY = (int)(radius * Math.sin((i-1)*theta + theta_o));
                enemyCounter.getAndIncrement();
            }
        }
        if (ListOfFighters.size() >= 30 && ListOfFighters.size() < 40){
            radius = (int)(30 * (50.0d)/(2.0d * 3.14d));
            theta = 2d * 3.14d/((double) 30);

            if (rotateTimer == Scroll.rotateTime){
                theta_o += 0.02;
            }
            for (int i = 1; i<= 30; i++) {
                ListOfFighters.get(i-1).inGroupX = (int)(radius * Math.cos((i-1)*theta + theta_o));
                ListOfFighters.get(i-1).inGroupY = (int)(radius * Math.sin((i-1)*theta + theta_o));
            }

            if (rotateTimer == Scroll.rotateTime) {
                theta_o_2 += 0.02;
            }

            radius_2 = (int)((ListOfFighters.size() - 30) * (50.0d)/(2.0d * 3.14d));
            theta_2 = 2d * 3.14d/((double) (ListOfFighters.size() - 30));

            for (int i = 1; i<= (ListOfFighters.size() - 30); i++) {
                ListOfFighters.get(i + 30 -1).inGroupX = (int)(radius_2 * Math.cos((i-1)*theta_2 + theta_o_2));
                ListOfFighters.get(i + 30 -1).inGroupY = (int)(radius_2 * Math.sin((i-1)*theta_2 + theta_o_2));
            }
        }
        if (ListOfFighters.size() >= 40){
            radius = (int)(ListOfFighters.size() * (50.0d)/(2.0d * 3.14d));
            theta = 2d * 3.14d/((double) ListOfFighters.size());

            center_x = x_coordinate + radius;
            center_y = y_coordinate + radius;

            if (rotateTimer == 10 && enemyGroupMover.phase > 1){
                theta_o += 0.01;
            }
            AtomicInteger enemyCounter = new AtomicInteger(0);
            for (int i = 1; i<= ListOfFighters.size(); i++) {
                ListOfFighters.get(i-1).inGroupX = (int)(radius * Math.cos((i-1)*theta + theta_o));
                ListOfFighters.get(i-1).inGroupY = (int)(radius * Math.sin((i-1)*theta + theta_o));
                enemyCounter.getAndIncrement();
            }
        }
        for (Enemy enemy: ListOfFighters){
            enemy.x_coordinate = this.x_coordinate + enemy.inGroupX;
            enemy.y_coordinate = this.y_coordinate + enemy.inGroupY;
//            enemy.destination_x = this.center_x + enemy.inGroupX;
//            enemy.destination_y = this.center_y + enemy.inGroupY;
//            enemy.destination_x = this.x_coordinate + enemy.inGroupX;
//            enemy.destination_y = this.y_coordinate + enemy.inGroupY;
        }
    }
}
