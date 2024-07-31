using UnityEngine;

public class CloudController : GameObject
{
    [Header("Movement Properties")]
    public float minVertical;
    public Boundary horizontalBoundary;
    public float maxOffscreenVertical;
    public float minOffscreenVertical;
    [Range(5.0f, 10.0f)]
    public float minVerticalSpeed;
    [Range(5.0f, 10.0f)]
    public float maxVerticalSpeed;
    [Range(-2.0f, 2.0f)]
    public float minHorizontalSpeed;
    [Range(-2.0f, 2.0f)]
    public float maxHorizontalSpeed;
    public float horizontalSpeed;


    protected override void ResetGameObject()
    {
        var randomXPosition = Random.Range(horizontalBoundary.min, horizontalBoundary.max);
        var randomYPosition = Random.Range(minOffscreenVertical, maxOffscreenVertical);
        horizontalSpeed = Random.Range(minHorizontalSpeed, maxHorizontalSpeed);
        verticalSpeed = Random.Range(minVerticalSpeed, maxVerticalSpeed);
        transform.position = new Vector3(randomXPosition, randomYPosition, 0.0f);
    }

    protected override void Move()
    {
        transform.position += new Vector3(-horizontalSpeed * Time.deltaTime, -verticalSpeed * Time.deltaTime, 0.0f);
    }

}
