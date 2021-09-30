import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:retail_agent_werf/screen/splashScreen.dart';
import 'package:retail_agent_werf/themes/myTheme.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      themeMode: ThemeMode.system,
      theme: ThemeData(
        colorScheme: ColorScheme.light(),
        primaryColor: Colors.lightBlue[800],
        fontFamily: GoogleFonts.lato().fontFamily,
      ),
      darkTheme: AppTheme.darkTheme,
      debugShowCheckedModeBanner: false,
      home: SplashScreen(),
    );
  }
}
