# kab library
android quik develop library,include a sample project
##How to use it
###init when app startup
    Kab.init(this, "db", 1)    
####will init kab library,database will named as "db",vision=1
####Bind in activity
    override void onCreate(Bundle savedState) {
            super.onCreate(savedInstanceState)
            Kab.bind(this, this)
        }
####or in fragment        
    override void onCreateView(...) {
                View root = super.onCreateView(inflater, container, savedInstanceState)
                Kab.bind(this, root)
            } 
####Don't forget UnBind when finalize
    override void onDestroy() {
            super.onDestroy()
            Kab.unbind(this)
        }
             
##SqliteORM table and view support
###create table and view
    @ViewName("Top5",selectAs="select * from device limited 5")
    class DeviceTop5 extends View{        
          @Column()
          public String name    
    }
    @TableName("device")
    class Device extends Table{        
        @Column()
        public String name    
    }
####Will create a view "Top5" and a table "device",each have columns "id" and "name"
###Save data to database,<View Object don't have a save function>
    Device d=new Device();
    d.name="dv";
    d.save();
####save device in table "device",call
    d.getId()
####get the id unique in table
###Load data from database
####get data by id
    Device d=Device.load(1)
####get data all the table or view
    List<Device> ds=Device.all();
####get data by where clause    
    List<Device> ds=Device.where("name=?","dv"); 
---
##Android view inject
###Bind views
    @Bind(R.id.XXX)
    ListView xxx;
###Bind control's event to function
    @OnClick(R.id.XX)
    void fun(View v){...}
####or bind multi control to same function
    @OnClick(R.id.XX,R.id.YY/*and others*/)
    void fun(View v){...}
####now only support android's
    onClick
    onLongClick
    onItemClick
---
##Android event bus
###first,you need create a message passer,such as a java POJO class
    class BusMessage{}
####this class used to distinct witch receiver will be pass to     
###second,add annotation on a member function where want receive the message
    @OnMessage()
    public void Rcv(BusMessage msg){}
###then,just broadcast the message by 
    Kab.bus.post(new BusMessage())
####just so simple,^-^
---
##A simple inject library
###Named a class you want to inject by @Named annotation
    @Named("god")
    class God implement IGod{}   
###Add annotation to where you want to get the object instance
    @Inject("god")
    IGod god;
####or if you want get multi object to an array
    @Inject("god"/*,others*/)
    Array<IGod> god;
####that's all!
---
##Simple reactive library
---
##Advance support    
###DataView simplify the logical and need less code
####just like samples above,get data just by sql
    @Dataview("select * from device where length(name)>10")
    DataView<Device> datas;
####or create views pojo object by you need
    class Vs extends View{@Column String name;}
    @Dataview("select * from device where length(name)=10")
    DataView<Vs> datas;
####then call DataView.update() get or refresh the object
    datas.update()
    
###ListView adapter effective 
####Create a holder class
    class Holder : com.lncosie.kab.widget.ListViewAdapter.ViewHolder<Device> {    
          @Bind(R.id.user_name)
          lateinit var user_name: TextView
    
          override int viewId(position: Int, data: Device){
            return R.layout.item_device;
           }
          override void bind(data: Device) {
              user_name.text = data.name
          }
      }
####define a dataview in class
   @Dataview("select * from device")
   DataView<Device> devices;
####and use it like this
   devices.update()
   ListAdapter adapter = new ListViewAdapter(this, devices, Holder::class.java);
   list.adapter = adapter
   