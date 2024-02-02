package Model.Values;

import Model.Types.RefType;
import Model.Types.Type;

public class RefValue implements Value{
    private final int heapAddress;
    private final Type innerReferencedType;

    public RefValue(int address,Type innerReferencedType){
        this.heapAddress = address;
        this.innerReferencedType = innerReferencedType;
    }

    public int getHeapAddress(){
        return this.heapAddress;
    }

    public Type getInnerReferencedType() {
        return this.innerReferencedType;
    }

    @Override
    public String toString() {
        return String.format("(%d, %s)", heapAddress, innerReferencedType);
    }

    @Override
    public Value deepCopy() {
        return new RefValue(heapAddress, innerReferencedType.deepCopy());
    }

    @Override
    public Type getType() {
        return new RefType(this.innerReferencedType);
    }
}
