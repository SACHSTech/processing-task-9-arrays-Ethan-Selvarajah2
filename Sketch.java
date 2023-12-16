import processing.core.PApplet;

public class Sketch extends PApplet {
  // Snowflake variables
	int intNumSnowflakes = 60;
  float[] fltSnowflakesX;
  float[] fltSnowflakesY;
  float[] fltSnowflakesSpeed;
  int[] intSnowflakesColour;
  float fltSnowfallSpeed = 1;

  // Player variables
  int intPlayerX;
  int intPlayerY;
  int intPlayerLives = 3;
  int intPlayerSize = 20;
  boolean boolPlayerIsColliding = false;

  // Extra game variables
  boolean[] boolBallHideStatus;
  boolean boolGameOver = false;

  /**
   * Sets the size of the window
   */
  public void settings() {
    size(800, 800);
  }

  /**
   * Initializes the variables and arrays
   */
  public void setup() {
    // Arrays
    fltSnowflakesX = new float[intNumSnowflakes];
    fltSnowflakesY = new float[intNumSnowflakes];
    fltSnowflakesSpeed = new float[intNumSnowflakes];
    intSnowflakesColour = new int[intNumSnowflakes];
    boolBallHideStatus = new boolean[intNumSnowflakes];

    // Give snowflakes a random speed and position
    for (int i = 0; i < intNumSnowflakes; i++) {
      fltSnowflakesX[i] = random(width);
      fltSnowflakesY[i] = random(-height, 0);
      fltSnowflakesSpeed[i] = random(1, 4);
      intSnowflakesColour[i] = color(255);
      boolBallHideStatus[i] = false;
    }

    // Initial player position
    intPlayerX = width / 2;
    intPlayerY = height - 50;
  }

  /**
   * The code that actually creates the drawings
   */
  public void draw() {
    // Background colour
    background(0);

    // Draw the snowflakes
    for (int i = 0; i < intNumSnowflakes; i++) {
      if(!boolBallHideStatus[i]) {
        fill(intSnowflakesColour[i]);
        ellipse(fltSnowflakesX[i], fltSnowflakesY[i], 40, 40);
        fltSnowflakesY[i] += fltSnowflakesSpeed[i] * fltSnowfallSpeed;

        if (fltSnowflakesY[i] > height) {
          fltSnowflakesY[i] = random(-height, 0);
          fltSnowflakesX[i] = random(width);
        }
      }
      
      // Collision check for player amd snowflakes
      if (!boolBallHideStatus[i] && !boolPlayerIsColliding && dist(intPlayerX, intPlayerY, fltSnowflakesX[i], fltSnowflakesY[i]) < intPlayerSize / 2 + 10) {
        intPlayerLives --;
        boolBallHideStatus[i] = true;
        boolPlayerIsColliding = true;
        
        if (intPlayerLives <= 0) {
          boolGameOver = true;
          break;
        }
      }

      boolPlayerIsColliding = false;

      // Draw plauer
      fill(0, 0, 255);
      ellipse(intPlayerX, intPlayerY, 20, 20);

      // Show player lives
      fill(255);
      textSize(20);
      text("Lives: " + intPlayerLives, width - 100, 30);

      // Draw player lives squares
      fill(255);
      for (int s = 0; s < intPlayerLives; s++){
        rect(width - 110 + (s * 30), 40, 20, 20);
      }

      // Game over screen
      if (boolGameOver) {
        background(255);
        fill(0);
        textAlign(CENTER);
        textSize(100);
        text("GAME OVER!", width / 2, height / 2);
      }
    }
  }

  /**
   * Handles all code related to key pressing, in this case, it works on the snowfall speed and player movement
   */
  public void keyPressed() {
    // Snowfall control
    if (keyCode == UP) {
      fltSnowfallSpeed -= 0.2;
    } else if (keyCode == DOWN) {
      fltSnowfallSpeed += 0.2;
    }

    // Player controls
    if (keyCode == 87) {
      intPlayerY -= 10;
    } else if (keyCode == 65) {
      intPlayerX -= 10;
    } else if (keyCode == 83) {
      intPlayerY += 10;
    } else if (keyCode == 68) {
      intPlayerX += 10;
    }
  }

  /**
   * Handles all code related to mouse pressing, ensures the snowflakes disappear on click
   */
  public void mousePressed() {
    if (!boolGameOver) {
      for (int i = 0; i < intNumSnowflakes; i++) {
        if (!boolBallHideStatus[i] && dist(mouseX, mouseY, fltSnowflakesX[i], fltSnowflakesY[i]) < 15) {
          boolBallHideStatus[i] = true;
        }
      }
    }
  }
}