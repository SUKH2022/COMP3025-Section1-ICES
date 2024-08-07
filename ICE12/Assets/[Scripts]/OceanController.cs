using UnityEngine;

public class OceanController : MyGameObject
{
    public Boundary boundary;
    public float verticalSpeed;

    protected override void Start()
    {
        ResetGameObject();
    }


    protected override void ResetGameObject()
    {
        transform.position = new Vector3(0.0f, boundary.max, 0.0f);
    }

    protected override void Move()
    {
        transform.position += new Vector3(0.0f, -verticalSpeed * Time.deltaTime, 0.0f);
    }

    protected override void CheckBounds()
    {
        if (transform.position.y <= boundary.min)
        {
            ResetGameObject();
        }
    }
}