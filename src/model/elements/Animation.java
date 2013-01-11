package model.elements;

import model.core.Coordinate;

public class Animation extends GameElement {
	private long timeOfLastFrame = 0;
	private int timePerFrame = 200; 
	private int frames;
	private int indexOfLastFrame = 0;
	private int frameHeight;

	public Animation(String imageURL, Coordinate position, int frames) {
		super(imageURL);
		this.setPosition(position);
		this.setFrames(frames);
		this.setFrameHeight((int) (this.getHeight()/frames));
	}

	public long getTimeOfLastFrame() {
		return timeOfLastFrame;
	}

	public void setTimeOfLastFrame(long timeOfLastFrame) {
		this.timeOfLastFrame = timeOfLastFrame;
	}

	public int getIndexOfLastFrame() {
		return indexOfLastFrame;
	}

	public void setIndexOfLastFrame(int indexOfLastFrame) {
		this.indexOfLastFrame = indexOfLastFrame;
	}

	public int getFrames() {
		return frames;
	}

	public void setFrames(int frames) {
		this.frames = frames;
	}

	public int getTimePerFrame() {
		return timePerFrame;
	}

	public void setTimePerFrame(int timePerFrame) {
		this.timePerFrame = timePerFrame;
	}

	public int getFrameHeight() {
		return frameHeight;
	}

	public void setFrameHeight(int frameHeight) {
		this.frameHeight = frameHeight;
	}
}
