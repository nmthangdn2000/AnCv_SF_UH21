import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:retail_agent_werf/components/slider/card_service.dart';
import 'package:retail_agent_werf/components/tabView/menu_tab_view.dart';
import 'package:retail_agent_werf/components/tabView/shop_tab_view.dart';
import 'package:retail_agent_werf/screen/signIn.dart';
import 'package:retail_agent_werf/utils/constants.dart';
import 'package:retail_agent_werf/utils/user_shared_preferences.dart';

class HomePage extends StatefulWidget {
  const HomePage({Key? key}) : super(key: key);

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  int _currentTabBar = 0;
  @override
  Widget build(BuildContext context) {
    return DefaultTabController(
      length: 3,
      child: Scaffold(
        body: SafeArea(
          child: CustomScrollView(
            slivers: <Widget>[
              SliverAppBar(
                bottom: PreferredSize(
                  child: _appBarTop(),
                  preferredSize: const Size.fromHeight(0),
                ),
                snap: false,
                toolbarHeight: 300,
                backgroundColor: Colors.white,
              ),
              _tabBar(),
              _tabBarView(),
            ],
          ),
        ),
      ),
    );
  }

  Container _headerHomePage() {
    return Container(
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          Row(
            children: [
              Container(
                width: 45,
                height: 45,
                child: CircleAvatar(
                  backgroundImage: AssetImage('assets/images/avata1.jpg'),
                  radius: 50.0,
                ),
              ),
              SizedBox(
                width: 10,
              ),
              Text(
                "Cô Hoa",
                style: TextStyle(
                  fontSize: 22,
                  fontWeight: FontWeight.w600,
                  color: Colors.black,
                ),
              ),
            ],
          ),
          GestureDetector(
            onTap: () {
              _signOut();
            },
            child: Container(
              padding: const EdgeInsets.all(10),
              decoration: BoxDecoration(
                color: Colors.grey.withOpacity(0.2),
                borderRadius: BorderRadius.circular(50),
              ),
              child: Icon(FontAwesomeIcons.signOutAlt),
            ),
          ),
        ],
      ),
    );
  }

  Widget _appBarTop() {
    return Container(
      child: Column(
        children: [
          Padding(
            padding: const EdgeInsets.symmetric(horizontal: 20.0),
            child: _headerHomePage(),
          ),
          SizedBox(
            height: 10,
          ),
          CardService(),
        ],
      ),
    );
  }

  Widget _tabBar() {
    return SliverAppBar(
      pinned: true,
      backgroundColor: Colors.white,
      elevation: 10,
      title: TabBar(
        labelColor: signInColor,
        unselectedLabelColor: Colors.black.withOpacity(.5),
        onTap: (index) {
          setState(() {
            _currentTabBar = index;
          });
        },
        tabs: [
          Tab(
            text: "Menu",
            icon: Icon(
              FontAwesomeIcons.bars,
              color: _currentTabBar == 0
                  ? signInColor
                  : Colors.black.withOpacity(.5),
            ),
          ),
          Tab(
            text: "Cửa hàng",
            icon: Icon(
              FontAwesomeIcons.store,
              color: _currentTabBar == 1
                  ? signInColor
                  : Colors.black.withOpacity(.5),
            ),
          ),
          Tab(
            text: "Thông báo",
            icon: Icon(
              FontAwesomeIcons.bell,
              color: _currentTabBar == 2
                  ? signInColor
                  : Colors.black.withOpacity(.5),
            ),
          ),
        ],
      ),
    );
  }

  Widget _tabBarView() {
    return SliverFillRemaining(
      child: TabBarView(
        children: [
          MenuTabView(),
          ShopTabView(),
          Center(
            child: Text("Bạn chưa có thông báo nào."),
          ),
        ],
      ),
    );
  }

  _signOut() async {
    await UserSharedPreferences.init();
    UserSharedPreferences.clear();
    return Navigator.of(context).pushAndRemoveUntil(
        MaterialPageRoute(builder: (BuildContext context) => SignIn()),
        (route) => false);
  }
}
