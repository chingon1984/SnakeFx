package com.chingon;

public class PositionAndDirection {
    private PositionCoordinates positionCoordinates;
    private Direction followingDirection;

    public PositionAndDirection(PositionCoordinates positionCoordinates, Direction followingDirection) {
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
