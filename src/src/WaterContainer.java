package src;

import java.awt.Color;
import java.awt.Graphics;

public class WaterContainer {
	public static Color emptyColor = new Color(242, 242, 242),
						borderColor = Color.black;
	private static int width = 20, height = 17;
	
	Color [] color = new Color[4];
	int number = 0, drawX, drawY, x, y;

	public WaterContainer(Color c[], int n, int x, int y) {
		boolean zero = false;
		number = n;
		this.x = x;
		this.y = y;
		drawX = x;
		drawY = y;
		for (int i = 0; i < 4; i++) {
			if (zero && c[i] != emptyColor) {
				for (int j = 0; j < 4; j++)
					color[j] = emptyColor;
				return;
			}
			color[i] = c[i];
			if (c[i] == emptyColor) {
				zero = true;
			}
		}
	}

	public boolean isEmpty() {
		for (int i = 0; i < 4; i++)
			if (color[i] != emptyColor)
				return false;
		return true;
	}
	public boolean isFullOneColor() {
		for (int i = 1; i < 4; i++)
			if (color[i] != color[0])
				return false;
		return true;
	}
	public boolean hasSpace() {
		return color[0] == emptyColor;
	}
	
	public Color getTopColor() {
		for (int i = 0; i < 4; i++) 
			if (color[i] != emptyColor)
				return color[i];
		return emptyColor;
	}
	public Color popColor() {
		for (int i = 0; i < 4; i++)
			if (color[i] != emptyColor) {
				Color c = color[i];
				color[i] = emptyColor;
				return c;
			}
		return emptyColor;
	}
	public void pushColor(Color c) {
		for (int i = 3; i >= 0; i--)
			if (color[i] == emptyColor) {
				color[i] = c;
				return;
			}
	}
	public void paint(Graphics g) {
		for (int i = 0; i < 4; i++) {
			g.setColor(color[i]);
			g.fillRect(drawX, drawY + (height * i), width, height);
			g.setColor(borderColor);
			g.drawRect(drawX, drawY + (height * i), width, height);
		}
	}
	public boolean isUnderMouse(int x, int y) {
		return (x >= this.x && x <= this.x + width && y >= this.y && y <= this.y + (4 * height));
	}
	public void moveTo(int x, int y) {
		drawX = x;
		drawY = y;
	}
	public void stopMoving() {
		drawX = x;
		drawY = y;
	}
}
