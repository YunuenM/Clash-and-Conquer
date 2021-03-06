import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Projectile class to 
 * 
 * @author Matthew Gong, Jerry Zhu
 * @version January 2022
 */
public class Projectile extends SuperSmoothMover
{
    //Instance variables
    protected int damage;
    protected double speed;
    protected Actor target;
    protected int lastX, lastY;
    
    /**
     * Constructor of the Projectile class
     * 
     * @param damage    The damage that the projectile will deal. 
     * @param speed     The speed of the projectile. 
     * @param target    The target of the projectile. 
     */
    public Projectile(int damage, double speed, Actor target){
        this.damage = damage;
        this.speed = speed;
        this.target = target;
        setImage("arrow.png");
    }
    
    /**
     * Act - do whatever the Projectile wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        turnTowardsTarget();
        move(speed);
        checkForHit();
    }
    
    /**
     * Check if the target has been hit. 
     */
    private void checkForHit(){
        if(target.getWorld() != null){
            if(this.intersects(target)){
                // Deals damage to target
                ((Level)getWorld()).removeObject(this);
                if (target instanceof Troop){
                    ((Troop)target).subtractHealth(10);
                }
                else if (target instanceof Castle){
                    ((Castle)target).subtractHealth(10);
                }
            }
        }
        else{
            if(findDistanceBetween(lastX, lastY) < speed) ((Level)getWorld()).removeObject(this);
        }
    }
    
    /**
     * Turns the Projectile towards the target if it is still in the World otherwise turn towards their last position
     */
    private void turnTowardsTarget(){
        if(target.getWorld() != null){
            turnTowards(target.getX(), target.getY());
            lastX = target.getX();
            lastY = target.getY();
        }
        else turnTowards(lastX, lastY);
    }
    
    /**
     * Find distance between another actor with a certain x and y coordinate. 
     * 
     * @param x      The x coordinate of the actor
     * @param y      The y coordinate of the actor
     */
    private double findDistanceBetween(double x, double y){
        return Math.sqrt(Math.pow(this.getX() - x, 2) + Math.pow(this.getY() - y, 2));
    }
}
