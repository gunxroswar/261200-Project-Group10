import java.util.ArrayList;

public class Territory {
    int m,n;
    long max_dep,interest_pct;
    Region[][] Territory = null;

    Territory(long m,long n,long max_dep,long interest_pct){
        this.m = (int) m;
        this.n = (int) n;
        this.max_dep = max_dep;
        this.interest_pct = interest_pct;
        Territory = new Region[this.m][this.n];
        for(int i =0;i<m;i++){
            for(int j=0;j<n;j++){
                Territory[i][j] = new Region(this.max_dep,this.interest_pct,i,j);
            }
        }
    }
}
