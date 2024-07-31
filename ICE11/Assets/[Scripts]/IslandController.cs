using UnityEngine;

public class IslandController : GameObject
{
    public Boundary horizontalBoundary;

    protected override void ResetGameObject()
    {
        var randomXPosition = Random.Range(horizontalBoundary.min, horizontalBoundary.max);
        transform.position = new Vector3(randomXPosition, verticalBoundary.max, 0.0f);
    }
}
