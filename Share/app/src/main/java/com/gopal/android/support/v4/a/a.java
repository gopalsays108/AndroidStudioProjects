package android.support.v4.a;

public final class a {
    public int a;
    public b b;
    public boolean c;
    public boolean d;
    public boolean e;
    public boolean f;

    public final void a(b bVar) {
        if (this.b == null) {
            throw new IllegalStateException("No listener register");
        } else if (this.b != bVar) {
            throw new IllegalArgumentException("Attempting to unregister the wrong listener");
        } else {
            this.b = null;
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(64);
        android.support.v4.c.a.a(this, sb);
        sb.append(" id=");
        sb.append(this.a);
        sb.append("}");
        return sb.toString();
    }
}
