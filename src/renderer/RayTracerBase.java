package renderer;
import primitives.Color;
import scene.Scene ;
import primitives.Ray;
/**
 * Abstract base class for ray tracing functionality.
 * This class provides the foundation for tracing rays in a scene
 * and calculating the resulting color.
 */
abstract public class  RayTracerBase {
    /**
     *  The scene to be traced.
     */
    protected final Scene scene;

    /**
     * Constructs a RayTracerBase with the specified scene.
     *
     * @param scene the scene to be traced
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }
    /**
     * Traces a ray and calculates the resulting color.
     *
     * @param ray the ray to trace
     * @return the color resulting from tracing the ray
     */
    abstract public Color traceRay (Ray ray);



}
