using UnityEngine;

public abstract class MyGameObject : MonoBehaviour
{

    protected virtual void Start()
    {

    }

    protected virtual void Update()
    {
        Move();
        CheckBounds();
    }

    protected virtual void ResetGameObject()
    {
        
    }
    protected virtual void Move()
    {
        
    }

    protected virtual void CheckBounds()
    {
        
    }
}
