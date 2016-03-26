package is.pedro;

import java.util.HashSet;

/**
 * Created by andri on 26/03/16.
 */
public class Pain {
    HashSet<Integer> painInfo = new HashSet<Integer>();
    HashSet<Integer> position = new HashSet<Integer>();
    HashSet<Integer> rPosition = new HashSet<Integer>();

    public Pain(HashSet<Integer> painInfo, HashSet<Integer> position, HashSet<Integer> rPosition) {
        this.painInfo = painInfo;
        this.position = position;
        this.rPosition = rPosition;
    }

    public Pain() { }
}
