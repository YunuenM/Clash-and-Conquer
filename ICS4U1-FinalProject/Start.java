import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Start class 
 * <p>
 * Starting World that is displayed on run. Displays buttons to play the game. 
 * 
 * <h1>Description</h1>
 * Clash and Conquer is a battle arena game with 14 levels which progressively get harder. 
 * After each level, there are upgrades in the shop which buff your troops, spells, and towers. 
 * Finish all 14 levels and conquer the map!
 * 
 * <h1>Features</h1>
 * <ol>
 *      <li>14 complete levels which scale in difficulty.</li>
 *      <li>9 playable cards.</li>
 *      <li>Shop menu with 18 unique upgrades to match card level, stored in an ArrayList.</li>
 *      <li>Player stats tracking (including level, win/loss ratio, gems spent, time played) stored on Greenfoot's UserInfo class.</li>
 *      <li>Leaderboard of top players with the UserInfo class (available on the Greenfoot Gallery). </li>
 *      <li>Queue used to rotate through cards in-game and for troops to target enemy players. </li>
 *      <li>3 different music tracks.</li>
 *      <li>Changable volume using a slider. </li>
 *      <li>Instructions page.</li>
 *      <li>Reset progress option.</li>
 * </ol>
 * 
 * @author Jerry Zhu
 * @version January 2022
 */
public class Start extends World
{
    // Instance variables
    private Image title;
    private Image startButton;
    private Image settingsButton;
    private Image shopButton;
    private Image statsButton;
    private Image instructionsButton;
    private Image background;
    private Image myKing;
    private Image enemyKing;
    private SuperTextBox label;
    private UserInfo user;
    private GreenfootSound music;
    private static final Color transparent = new Color(0, 0, 0, 0);
    
    /**
     * Constructor for objects of class MyWorld.
     */
    public Start()
    {    
        // Create a new world with 900 x 600 cells with a cell size of 1x1 pixels.
        super(900, 600, 1); 
        
        background = new Image(new GreenfootImage("Worlds/startbackground.png"));
        addObject(background, getWidth() / 2, getHeight() / 2);
        
        myKing = new Image(new GreenfootImage("myking.png"));
        myKing.getImage().scale(150, 200);
        addObject(myKing, 100, 150);
        
        enemyKing = new Image(new GreenfootImage("enemyking.png"));
        addObject(enemyKing, 800, 150);
        
        // Initialize static images to the World
        title = new Image(new GreenfootImage("starttext.png"));
        addObject(title, getWidth() / 2, 150);
        
        settingsButton = new Image(new GreenfootImage("Buttons/settings.png"));
        settingsButton.getImage().scale(250, 100);
        addObject(settingsButton, 150, 350);
        
        shopButton = new Image(new GreenfootImage("Buttons/shop.png"));
        shopButton.getImage().scale(250, 100);
        addObject(shopButton, 450, 350);
        
        statsButton = new Image(new GreenfootImage("Buttons/stats.png"));
        statsButton.getImage().scale(250, 100);
        addObject(statsButton, 750, 350);
        
        // Initialize the image of the start button and add it to the world
        instructionsButton = new Image(new GreenfootImage("Buttons/instructions.png"));
        instructionsButton.getImage().scale(320, 100);
        addObject(instructionsButton, 280, 500);
        
        // Initialize the image of the start button and add it to the world
        startButton = new Image(new GreenfootImage("Buttons/play.png"));
        startButton.getImage().scale(200, 100);
        addObject(startButton, 600, 500);
        
        String str;
        
        if (UserInfo.isStorageAvailable()){
            user = UserInfo.getMyInfo();
            String check = user.getString(0);
            if (check.equals("") || check == null){
                user.setScore(1); // Level
                user.setInt(0, 0); // Gems
                user.setInt(1, 1); // Difficulty
                user.setInt(2, 50); // Volume
                user.setInt(3, 0); // Music type
                user.setInt(4, 0); // Elixir bar speed
                user.setInt(5, 0); // Castle health
                user.setInt(6, 0); // Castle projectile attack
                user.setInt(7, 0); // Wins
                user.setInt(8, 0); // Losses
                user.setInt(9, 0); // Time elapsed (seconds)
                user.setString(0, "0 0 0 0 0 0 0 0 "); // Knight, archer, giant, skeleton (health, attack)
                user.setString(1, "0 0 0 0 "); // Towers (elixir, tombstone)
                user.setString(2, "0 0 0 0 "); // Spells (fireball, poison)
                user.setString(3, "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 "); // 15 levels
                user.store();
                if (music != null){
                    music.setVolume(user.getInt(2));
                }
            }
            str = "Welcome " + user.getUserName() + "!";
        }
        else {
            str = "Plase login to unlock the full game.";
        }
        Font font = new Font("Verdana", 26);
        label = new SuperTextBox(str, transparent, Color.BLACK, font, false, 18 * str.length(), 0, transparent);
        addObject(label, getWidth()/2, 260);
    }
    
    /**
     * When World is created, start the music and set the volume. 
     */
    public void started(){
        if (UserInfo.isStorageAvailable()){
            user = UserInfo.getMyInfo();
            if (user.getInt(3) == 0){
                if (music != null){
                    music.stop();
                }
            }
            else {
                if (music != null){
                    music.stop();
                }
                music = new GreenfootSound("mainsong" + user.getInt(3) + ".mp3");
                music.playLoop();
            }
            if (music != null){
                music.setVolume(user.getInt(2));
            }
        }
    }
    
    /**
     * Act method to be run each iteration. 
     */
    public void act(){
        checkClick();
    }
    
    /**
     * Check if any buttons are clicked and load the corresponding Worlds if necessary.
     */
    public void checkClick(){
        if (Greenfoot.mouseClicked(startButton)) {
            LevelMenu levelMenu = new LevelMenu();
            if (music != null){
                music.stop();
            }
            levelMenu.started();
            Greenfoot.setWorld(levelMenu);
        }
        else if (Greenfoot.mouseClicked(settingsButton)){
            Settings settings = new Settings();
            if (music != null){
                music.stop();
            }
            settings.started();
            Greenfoot.setWorld(settings);
        }
        else if (Greenfoot.mouseClicked(shopButton)){
            Shop shop = new Shop();
            if (music != null){
                music.stop();
            }
            shop.started();
            Greenfoot.setWorld(shop);
        }
        else if (Greenfoot.mouseClicked(statsButton)){
            Stats stats = new Stats();
            if (music != null){
                music.stop();
            }
            stats.started();
            Greenfoot.setWorld(stats);
        }
        else if (Greenfoot.mouseClicked(instructionsButton)){
            Instructions instructions = new Instructions();
            if (music != null){
                music.stop();
            }
            instructions.started();
            Greenfoot.setWorld(instructions);
        }
    }
}
