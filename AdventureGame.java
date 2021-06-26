import java.util.Random;
import java.util.Scanner;

public class AdventureGame {
    
    public static void main(String[] args) {
        // System objects
        Scanner in = new Scanner(System.in); // scanner for user input
        Random rand = new Random(); 
        final int PERCENT = 100; 
        int score = 0; // enemies killed
        int turn = 0; // turn counter
        
        // Enemies
        String[] enemies = {"Skeleton", "Zombie", "Warrior", "Assassin", "Vampire", "Dragon", "Werewolf",
                            "Ghost", "Spider", "Mummy", "Snake"}; // array of enemy names
        int maxEnemyHealth = 100; // maximum enemy health
        int minEnemyHealth = 1; // minimum enemy health
        int enemyAttackDamage = 50; // maximum enemy attack damage

        // Player
        int playerHealth = 250; // current player health amount
        int maxPlayerHealth = 250; // maximum health the player can have
        int playerAttackDamage = 40; // maximum player attack damage
        int playerRunChanceWithoutHit = 90; // Percentage, run chance without being hit by the enemy
        String playerName = ""; // placeholder for the player's name

        // Health potions
        int numOfHealthPotions = 3; // number of health potions the player has
        int healthPotionHealAmount = 25; // health potion heal amount
        int healthPotionDropChance = 20; // Percentage, health potion drop chance after defeating an enemy

        //boss
        String[] bosses = {"Three-Headed Dragon", "Giant Alien"}; // array of boss names
        int maxBossHealth = 300; // boss maximum health
        int minBossHealth = 80; // boss minimum health
        int bossAttackDamage = 80; // maximum boss attack damage
        int bossEncounter = 3; // determines when the player encounters a boss
        int bossOffset = 3; // after every 3 regular enemies that have been defeated the player will face a boss
        int bossScore = 0; // counts how many bosses the player has defeated

        // Game
        boolean running = true;
        String divider = "---------------------------------------------"; 
        System.out.println("\tWelcome to the Creepy Cave");
        System.out.println(divider + "\n");
        System.out.print("Enter your name: ");
        playerName = in.nextLine(); // user enters player's name
        System.out.println("\nAre you sure you want to start? There are many dangers ahead.\n");
        System.out.print("Yes or No?\n");
        while(true) {
            String confirmation = in.next(); // user enters confirmation
            if(confirmation.toLowerCase().equals("no")) {
                endGame(in, playerName, score, turn, bossScore);
            } else if(confirmation.toLowerCase().equals("yes")) {
                System.out.println("\nGoodluck " + playerName + " you'll need it!!!");
                System.out.println(divider + "\n");
                break;
            } else {
                System.out.println("Invalid Command!");  
            }
        }

        GAME:
        while(running) { // while the game is running

            if(score == bossEncounter) {
                int bossHealth = rand.nextInt((maxBossHealth - minBossHealth) + 1) + minBossHealth; // random int for boss health
                String bossName = bosses[rand.nextInt(bosses.length)]; // random boss name from boss array

                System.out.println(divider);
                System.out.println("\tBoss: " + bossName + " has appeared!!!\n");

                while(bossHealth > 0) { // fighting a boss
                    turn++;

                    System.out.println("\tYour HP: " + playerHealth);
                    System.out.println("\t" + bossName + "'s HP: " + bossHealth);
                    System.out.println("\n\tWhat would you like to do?");
                    System.out.println("\t1. Attack");
                    System.out.println("\t2. Drink health potion");

                    String input = in.next(); // user enters what they want to do
                    if(input.equals("1")) { // when user attacks
                        int damageDealt = rand.nextInt(playerAttackDamage); // random int for the damage dealt to the boss
                        int damageTaken = rand.nextInt(bossAttackDamage); // random int for the damage dealth to the player

                        bossHealth -= playerAttackDamage; 
                        playerHealth -= bossAttackDamage;

                        System.out.println("\tYou strike the " + bossName + " for " + damageDealt + " damage.");
                        System.out.println("\tYou receive " + damageTaken + " damage from the " + bossName + "\n");

                        if(bossHealth <= 0) { // when boss is defeated
                            System.out.println("\tThe " + bossName + " was defeated!!!\n");
                            System.out.println(divider);
                            bossScore++; // increase the count of bosses defeated
                            bossEncounter += bossOffset; // decide when the player faces the next boss
                        } else if(playerHealth <= 0) { // when the player is defeated by the boss
                            System.out.println("\tYou have died!\n");
                            endGame(in, playerName, score, turn, bossScore);
                        }
                    } else if(input.equals("2")) { // when user selects health potion
                        if(numOfHealthPotions > 0) { // if the player has health potion/s left
                            if(playerHealth < maxPlayerHealth) { // if player doesn't have max health
                                if(playerHealth + healthPotionHealAmount >= maxPlayerHealth) { // stops the player from going above max health 
                                    playerHealth = maxPlayerHealth;
                                } else {
                                    playerHealth += healthPotionHealAmount; // increses player health by the heal amount
                                }
                                numOfHealthPotions--; // decreases number of health potions
                                System.out.println("\tYou drink a health potion, you have healed for " + healthPotionHealAmount + ".");
                                System.out.println("\tYou now have " + playerHealth + " HP.");
                                System.out.println("\tYou have " + numOfHealthPotions + " health potions left.\n"); 
                            } else {
                                System.out.println("\tYou are at max health!\n");
                            }
                        } else {
                            System.out.println("\tYou have no health potions left! Defeat enemies for a chance to get one.\n");
                        }
                    } else {
                        System.out.println("\tInvalid Command!\n");
                    }
                }

            }

            int enemyHealth = rand.nextInt((maxEnemyHealth - minEnemyHealth) + 1) + minEnemyHealth; // random int for enemy health
            String enemy = enemies[rand.nextInt(enemies.length)]; // random enemy name from enemies array

            System.out.println("\t" + enemy + " has appeared!\n");

            while(enemyHealth > 0) { // fighting an enemy
                turn++;

                System.out.println("\tYour HP: " + playerHealth);
                System.out.println("\t" + enemy + "'s HP: " + enemyHealth);
                System.out.println("\n\tWhat would you like to do?");
                System.out.println("\t1. Attack");
                System.out.println("\t2. Drink health potion");
                System.out.println("\t3. Run");

                String input = in.next(); // user enters decides what they want to do
                if(input.equals("1")) { // attack
                    int damageDealt = rand.nextInt(playerAttackDamage); // random int for damage dealth by the player
                    int damageTaken = rand.nextInt(enemyAttackDamage); // random int for the damage the player receives

                    enemyHealth -= damageDealt;
                    playerHealth -= damageTaken;

                    System.out.println("\tYou strike the " + enemy + " for " + damageDealt + " damage.");
                    System.out.println("\tYou receive  " + damageTaken + " damage from the " + enemy + "\n");
                    if(enemyHealth <= 0) { // when the enemy is defeated
                        System.out.println("\t" + enemy + " was defeated!");
                        score++;
                    } else if(playerHealth <= 0) { // when the player is defeated
                        break;
                    }
                } else if(input.equals("2")) { // drink health potion
                    if(numOfHealthPotions > 0) { // checks if the user has any health potions
                        if(playerHealth < maxPlayerHealth) { // checks the player doesn't have max health
                            if(playerHealth + healthPotionHealAmount >= maxPlayerHealth) { // ensures the player doesn't go above max health
                                playerHealth = maxPlayerHealth;
                            } else {
                                playerHealth += healthPotionHealAmount; // increases the player health by the heal amount
                            }
                            numOfHealthPotions--; // decreses the number of health potions
                            System.out.println("\tYou drink a health potion, you have healed for " + healthPotionHealAmount + ".");
                            System.out.println("\tYou now have " + playerHealth + " HP.");
                            System.out.println("\tYou have " + numOfHealthPotions + " health potions left.\n"); 
                        } else {
                            System.out.println("\tYou are at max health!\n");
                        }
                    } else {
                        System.out.println("\tYou have no health potions left! Defeat enemies for a chance to get one.\n");
                    }
                } else if(input.equals("3")) { // run
                    if(rand.nextInt(PERCENT) < playerRunChanceWithoutHit) { // determines if the player receives a hit from the enemy when running
                        System.out.println("\tYou run away from the " + enemy + "!\n");
                        continue GAME;
                    } else {
                        System.out.println("\tYou run away from the " + enemy + " but got hit!\n");
                        int damageTaken = rand.nextInt(enemyAttackDamage);
                        playerHealth -= damageTaken;
                        if(playerHealth <= 0) { // if the player is defeated by the hit after running
                            break;
                        } else {
                            continue GAME;
                        }
                    }
                } else {
                    System.out.println("\tInvalid Command!\n");
                }

            }
            if(playerHealth <= 0) { // when the player is defeated
                System.out.println("\tYou have died!\n");
                endGame(in, playerName, score, turn, bossScore);
            }
            
            System.out.println("---------------------------------------------");
            if(rand.nextInt(PERCENT) < healthPotionDropChance) { // determines if the enemy, once defeated, drops a health potion
                numOfHealthPotions++; // increases the number of health potions that the player has
                System.out.println("\tThe enemy dropped a health potion!");
                System.out.println("\tYou now have " + numOfHealthPotions + " health potions(s) left.");
            }
            
            System.out.println("\tYou have " + playerHealth + " HP left."); 
            
            System.out.println("---------------------------------------------");
            System.out.println("\tWhat would you like to do?");
            System.out.println("\t1. Continue fighting");
            System.out.println("\t2. Exit Cave");
            
            String userInput = in.next(); // user decides if they want to continue or quit the game after defeating an enemy
            
            while(!userInput.equals("1") && !userInput.equals("2")) { // checks that the user's input is valid
                System.out.println("\tInvalid Command!\n");
                userInput = in.next();
            }
            
            if(userInput.equals("1")) { // continue fighting
                System.out.println("\tYou continue on your adventure!\n");
            } else if(userInput.equals("2")) { // exit
                System.out.println("\tYou exit the cave, successful from your adventures!\n");
                endGame(in, playerName, score, turn, bossScore);
            }
        }
            endGame(in, playerName, score, turn, bossScore);

    }

    // end of game, shows credits and stats
    public static void endGame(Scanner in, String playerName, int score, int turn, int bossScore) {
        System.out.println("###############");
        System.out.println("\tTHANKS FOR PLAYING! " + playerName);
        System.out.println("\tYou earned a score of: " + score);
        System.out.println("\tYBosses defeated " + bossScore);
        System.out.println("\tTurn: " + turn);
        System.out.println("###############");
        in.close();
        System.exit(0); // ends program
    }



}