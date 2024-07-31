using UnityEngine;

public class GameObject : MonoBehaviour
{
    public Boundary verticalBoundary;
    public float verticalSpeed;

    protected virtual void Start()
    {
        ResetGameObject();
    }

    protected virtual void Update()
    {
        Move();
        CheckBounds();
    }

    protected virtual void ResetGameObject()
    {
        transform.position = new Vector3(transform.position.x, verticalBoundary.max, transform.position.z);
    }
    protected virtual void Move()
    {
        transform.position += new Vector3(0.0f, -verticalSpeed * Time.deltaTime, 0.0f);
    }

    protected virtual void CheckBounds()
    {
        if (transform.position.y <= verticalBoundary.min)
        {
            ResetGameObject();
        }
    }
}
