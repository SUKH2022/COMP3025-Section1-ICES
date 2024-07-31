using UnityEngine;

public class PlayerBehaviour : GameObject
{
    [Header("Movement Properties")]
    public float horizontalSpeed;
    public float verticalPosition;
    public Boundary horizontalBoundary;
    [Header("Sound FX")]
    public AudioSource yaySound;
    public AudioSource thunderSound;

    protected override void Start()
    {
        transform.position = new Vector3(0.0f, verticalPosition, 0.0f);
    }

    protected override void Move()
    {
        if (Input.touchCount > 0)
        {
            // gets input touches from screen space in pixels
            var x = Input.touches[0].position.x;

            // converts screen space to world space
            var horizontalPosition = Camera.main.ScreenToWorldPoint(new Vector3(x, 0.0f, 0.0f)).x;

            transform.position = new Vector3(horizontalPosition, verticalPosition, 0.0f);
        }
    }

    protected override void CheckBounds()
    {
        if (transform.position.x <= horizontalBoundary.min)
        {
            transform.position = new Vector3(horizontalBoundary.min, verticalPosition, 0.0f);
        }
        else if (transform.position.x >= horizontalBoundary.max)
        {
            transform.position = new Vector3(horizontalBoundary.max, verticalPosition, 0.0f);
        }
    }
    void OnTriggerEnter2D(Collider2D other)
    {
        if (other.gameObject.CompareTag("Island"))
        {
            yaySound.Play();
        }
        else if (other.gameObject.CompareTag("Cloud"))
        {
            thunderSound.Play();
        }
    }
}
