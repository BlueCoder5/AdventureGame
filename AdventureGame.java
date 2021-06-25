import java.util.Random;
import java.util.Scanner;

public class AdventureGame {
    
    public static void main(String[] args) {
        // System objects
        Scanner in = new Scanner(System.in);
        Random rand = new Random();
        final int PERCENT = 100;
        int score = 0; // enemies killed
        int turn = 0; // turn counter
        
        // Enemies
        String[] enemies = {"Skeleton", "Zombie", "Warrior", "Assassin", "Vampire", "Dragon", "Werewolf",
                            "Ghost", "Spider", "Mummy", "Snake"};
        int maxEnemyHealth = 100;
        int minEnemyHealth = 1;
        int enemyAttackDamage = 50;

        // Player
        int playerHealth = 1000;
        int maxPlayerHealth = 1000;
        int playerAttackDamage = 40;
        int playerRunChanceWithoutHit = 90; // Percentage
        String playerName = "";

        // Health potions
        int numOfHealthPotions = 3;
        int healthPotionHealAmount = 25;
        int healthPotionDropChance = 20; // Percentage

        //boss
        String[] bosses = {"Three-Headed Dragon", "Giant Alien"};
        int maxBossHealth = 300;
        int minBossHealth = 80; 
        int bossAttackDamage = 80;
        int bossEncounter = 3;
        int bossOffset = 3;
        int bossScore = 0; 

        // Game
        boolean running = true;
        String divider = "---------------------------------------------";
        System.out.println("\tWelcome to the Creepy Cave");
        System.out.println(divider + "\n");
        System.out.print("Enter your name: ");
        playerName = in.nextLine();
        System.out.println("\nAre you sure you want to start? There are many dangers ahead.\n");
        System.out.print("Yes or No?\n");
        while(true) {
            String confirmation = in.next();
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
        while(running) {

            if(score == bossEncounter) {
                int bossHealth = rand.nextInt((maxBossHealth - minBossHealth) + 1) + minBossHealth;
                String bossName = bosses[rand.nextInt(bosses.length)];

                System.out.println(divider);
                System.out.println("\tBoss: " + bossName + " has appeared!!!\n");

                while(bossHealth > 0) {
                    turn++;

                    System.out.println("\tYour HP: " + playerHealth);
                    System.out.println("\t" + bossName + "'s HP: " + bossHealth);
                    System.out.println("\n\tWhat would you like to do?");
                    System.out.println("\t1. Attack");
                    System.out.println("\t2. Drink health potion");

                    String input = in.next();
                    if(input.equals("1")) {
                        int damageDealt = rand.nextInt(playerAttackDamage);
                        int damageTaken = rand.nextInt(bossAttackDamage);

                        bossHealth -= playerAttackDamage;
                        playerHealth -= bossAttackDamage;

                        System.out.println("\tYou strike the " + bossName + " for " + damageDealt + " damage.");
                        System.out.println("\tYou receive " + damageTaken + " damage from the " + bossName + "\n");

                        if(bossHealth <= 0) {
                            System.out.println("\tThe " + bossName + " was defeated!!!\n");
                            System.out.println(divider);
                            bossScore++;
                            bossEncounter += bossOffset;
                        } else if(playerHealth <= 0) {
                            System.out.println("\tYou have died!\n");
                            endGame(in, playerName, score, turn, bossScore);
                        }
                    } else if(input.equals("2")) {
                        if(numOfHealthPotions > 0) {
                            if(playerHealth < maxPlayerHealth) {
                                if(playerHealth + healthPotionHealAmount >= maxPlayerHealth) {
                                    playerHealth = maxPlayerHealth;
                                } else {
                                    playerHealth += healthPotionHealAmount;
                                }
                                numOfHealthPotions--;
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

            int enemyHealth = rand.nextInt((maxEnemyHealth - minEnemyHealth) + 1) + minEnemyHealth;
            String enemy = enemies[rand.nextInt(enemies.length)];

            System.out.println("\t" + enemy + " has appeared!\n");

            while(enemyHealth > 0) {
                turn++;

                System.out.println("\tYour HP: " + playerHealth);
                System.out.println("\t" + enemy + "'s HP: " + enemyHealth);
                System.out.println("\n\tWhat would you like to do?");
                System.out.println("\t1. Attack");
                System.out.println("\t2. Drink health potion");
                System.out.println("\t3. Run");

                String input = in.next();
                if(input.equals("1")) {
                    int damageDealt = rand.nextInt(playerAttackDamage);
                    int damageTaken = rand.nextInt(enemyAttackDamage);

                    enemyHealth -= damageDealt;
                    playerHealth -= damageTaken;

                    System.out.println("\tYou strike the " + enemy + " for " + damageDealt + " damage.");
                    System.out.println("\tYou receive  " + damageTaken + " damage from the " + enemy + "\n");
                    if(enemyHealth <= 0) {
                        System.out.println("\t" + enemy + " was defeated!");
                        score++;
                    } else if(playerHealth <= 0) {
                        break;
                    }
                } else if(input.equals("2")) {
                    if(numOfHealthPotions > 0) {
                        if(playerHealth < maxPlayerHealth) {
                            if(playerHealth + healthPotionHealAmount >= maxPlayerHealth) {
                                playerHealth = maxPlayerHealth;
                            } else {
                                playerHealth += healthPotionHealAmount;
                            }
                            numOfHealthPotions--;
                            System.out.println("\tYou drink a health potion, you have healed for " + healthPotionHealAmount + ".");
                            System.out.println("\tYou now have " + playerHealth + " HP.");
                            System.out.println("\tYou have " + numOfHealthPotions + " health potions left.\n"); 
                        } else {
                            System.out.println("\tYou are at max health!\n");
                        }
                    } else {
                        System.out.println("\tYou have no health potions left! Defeat enemies for a chance to get one.\n");
                    }
                } else if(input.equals("3")) {
                    if(rand.nextInt(PERCENT) < playerRunChanceWithoutHit) {
                        System.out.println("\tYou run away from the " + enemy + "!\n");
                        continue GAME;
                    } else {
                        System.out.println("\tYou run away from the " + enemy + " but got hit!\n");
                        int damageTaken = rand.nextInt(enemyAttackDamage);
                        playerHealth -= damageTaken;
                        if(playerHealth <= 0) {
                            break;
                        } else {
                            continue GAME;
                        }
                    }
                } else {
                    System.out.println("\tInvalid Command!\n");
                }

            }
            if(playerHealth <= 0) {
                System.out.println("\tYou have died!\n");
                endGame(in, playerName, score, turn, bossScore);
            }
            
            System.out.println("---------------------------------------------");
            if(rand.nextInt(PERCENT) < healthPotionDropChance) {
                numOfHealthPotions++;
                System.out.println("\tThe enemy dropped a health potion!");
                System.out.println("\tYou now have " + numOfHealthPotions + " health potions(s) left.");
            }
            
            System.out.println("\tYou have " + playerHealth + " HP left."); 
            
            System.out.println("---------------------------------------------");
            System.out.println("\tWhat would you like to do?");
            System.out.println("\t1. Continue fighting");
            System.out.println("\t2. Exit Cave");
            
            String userInput = in.next();
            
            while(!userInput.equals("1") && !userInput.equals("2")) {
                System.out.println("\tInvalid Command!\n");
                userInput = in.next();
            }
            
            if(userInput.equals("1")) {
                System.out.println("\tYou continue on your adventure!\n");
            } else if(userInput.equals("2")) {
                System.out.println("\tYou exit the cave, successful from your adventures!\n");
                endGame(in, playerName, score, turn, bossScore);
            }

        }
            endGame(in, playerName, score, turn, bossScore);

    }

    // end of game credits and stats
    public static void endGame(Scanner in, String playerName, int score, int turn, int bossScore) {
        System.out.println("###############");
        System.out.println("\tTHANKS FOR PLAYING! " + playerName);
        System.out.println("\tYou earned a score of: " + score);
        System.out.println("\tYou defeated " + bossScore + " bosses.");
        System.out.println("\tTurn: " + turn);
        System.out.println("###############");
        in.close();
        System.exit(0);
    }



}