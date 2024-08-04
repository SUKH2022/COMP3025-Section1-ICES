using UnityEngine;

public class OceanController : MyGameObject
{
    public Boundary boundary;
    public float verticalSpeed;

    protected override void Start()
    {
        ResetGameObject();
    }


    void ResetGameObject()
    {
        transform.position = new Vector3(0.0f, boundary.max, 0.0f);
    }

    void Move()
    {
        transform.position += new Vector3(0.0f, -verticalSpeed * Time.deltaTime, 0.0f);
    }

    void CheckBounds()
    {
        if (transform.position.y <= boundary.min)
        {
            ResetGameObject();
        }
    }
}