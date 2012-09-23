package test;

import game.Tile;

import java.awt.image.BufferedImage;

public class ChunkData {

	public BufferedImage chunk(Tile[] t, int sizeUntilNull) {
		int[] xCoords = new int[sizeUntilNull];
		int[] yCoords = new int[sizeUntilNull];
		int tempX = 0;
		int tempY = 0;
		for(int i = 0; i < sizeUntilNull; i++) {
			xCoords[i] = t[i].getX();
			yCoords[i] = t[i].getY();
		}
		int[] tempXCopy = xCoords.clone();
		int[] tempYCopy = yCoords.clone();

		for(int j = 0; j < sizeUntilNull; j++) {
			for(int k = 0; k < sizeUntilNull; k++) {
				tempX = Math.max(tempXCopy[j], xCoords[k]);
				tempY = Math.max(tempYCopy[j], yCoords[k]);
			}
		}
		BufferedImage b = new BufferedImage(tempX+32, tempY+32, BufferedImage.TYPE_INT_ARGB);
		for(int k = 0; k < sizeUntilNull; k++) {
			System.out.println(t[k].getX() + " " + t[k].getY());
			b.createGraphics().drawImage(t[k].getTile(), t[k].getX(), t[k].getY(), null);
		}
		return b;
	}
	
}
