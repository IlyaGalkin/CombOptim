#include <iostream>

#include <bits/stdc++.h>
#define fo(i,a,b) for(int i=a;i<=b;++i)
#define fod(i,a,b) for(int i=a;i>=b;--i)
#define N 500005
using namespace std;
int od[N],ev[N],n1,n,m,m1,a[4*N][4],last[2*N],ans,pt[2*N],sz[2*N];// инициализация переменных
struct node//структура
{
	int w;
	friend bool operator <(node x,node y)
	{
		return a[x.w][2]>a[y.w][2];
	}
};
priority_queue<node> h;//задаем приоритет
vector<int> d[2*N];
int lev(int k) {return(k&1)?k+1:k;}
int lod(int k) {return(k&1)?k:k+1;}
int sev(int k) {return(k&1)?k-1:k;}
int sod(int k) {return(k&1)?k:k-1;}
void read(int &x)//считывающая функция
{
	x=0;char ch=getchar();
	while(ch<'0'||ch>'9') ch=getchar();
	while(ch>='0'&&ch<='9') x=(x<<3)+(x<<1)+ch-'0',ch=getchar();
}
void link(int x,int y,int l,int r)//по сути деталь к считыванию) преобразует считаные данные
{
	if(l>r) return;
	a[++m1][0]=x,a[m1][1]=y,a[m1][2]=l,a[m1][3]=r;
	h.push((node){m1});
}
void update(int k,int st)//
{
	while(pt[k]<sz[k]&&a[d[k][pt[k]]][2]<=last[k])
	{
		int q=d[k][pt[k]];pt[k]++;
		a[q][2]=max(a[q][2],st);
		if(a[q][2]<=a[q][3]) h.push((node){q});
	}
}
int main()
{
	cin>>n>>m;
	if(n==1) {printf("0\n");return 0;}
	fo(i,1,n) od[i]=++n1,ev[i]=++n1;
	fo(i,1,m)
	{
		int x,y,l,r;
		read(x),read(y),read(l),read(r);
		r--;
		link(od[x],ev[y],lod(l),sod(r));
		link(ev[x],od[y],lev(l),sev(r));
		link(od[y],ev[x],lod(l),sod(r));
		link(ev[y],od[x],lev(l),sev(r));
	}
	memset(last,128,sizeof(last));
	last[ev[1]]=0;
	while(!h.empty())
	{
		int w=h.top().w;h.pop();
		if(last[a[w][0]]>=a[w][2])
		{
			if(a[w][1]==od[n]||a[w][1]==ev[n])
			{
				ans=a[w][2]+1;
				printf("%d\n",ans);
				return 0;
			}
			last[a[w][0]]=max(last[a[w][0]],a[w][3]);
			last[a[w][1]]=max(last[a[w][1]],a[w][3]+1);
			update(a[w][1],a[w][2]+1);
		}
		else d[a[w][0]].push_back(w),sz[a[w][0]]++;
	}
	printf("-1\n");
}
