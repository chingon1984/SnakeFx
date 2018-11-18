package com.chingon;

public class HeadMovement {
    private PositionCoordinates positionCoordinates;
    private Direction followingDirection;

    public HeadMovement(PositionCoordinates positionCoordinates, Direction followingDirection) {
        this.positionCoordinates = positionCoordinates;
        this.followingDirection = followingDirection;
    }

    public PositionCoordinates getPositionCoordinates() {
        return positionCoordinates;
    }

    public Direction getFollowingDirection() {
        return followingDirection;
    }
}
