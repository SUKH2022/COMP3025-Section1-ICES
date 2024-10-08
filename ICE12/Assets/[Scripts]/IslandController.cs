using UnityEngine;

public class IslandController : MyGameObject
{
    public float maxVertical;
    public float minVertical;
    public float maxHorizontal;
    public float minHorizontal;
    public float verticalSpeed;

    protected override void Start()
    {
        ResetGameObject();
    }


    protected override void ResetGameObject()
    {
        var randomXPosition = Random.Range(minHorizontal, maxHorizontal);
        transform.position = new Vector3(randomXPosition, maxVertical, 0.0f);
    }

    protected override void Move()
    {
        transform.position += new Vector3(0.0f, -verticalSpeed * Time.deltaTime, 0.0f);
    }

    protected override void CheckBounds()
    {
        if (transform.position.y <= minVertical)
        {
            ResetGameObject();
        }
    }
}