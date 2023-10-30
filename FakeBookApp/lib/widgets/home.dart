import 'dart:convert';

import 'package:customer_app/utils/.constants.example.dart';
import 'package:customer_app/utils/utils.dart';
import 'package:customer_app/widgets/blog/create.dart';
import 'package:customer_app/widgets/detail.dart';
import 'package:customer_app/widgets/profile.dart';
import 'package:customer_app/widgets/search.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:customer_app/widgets/list.dart';
import 'package:http/http.dart' as http;

import 'favorite.dart';

class ListBlog {
  final int id;
  final String user;
  final String avt;
  final int comments;
  final int shares;
  final int likes;
  final int views;
  final String content;
  final String thumbnail;

  ListBlog({
    required this.id,
    required this.user,
    required this.avt,
    required this.comments,
    required this.shares,
    required this.likes,
    required this.views,
    required this.content,
    required this.thumbnail,
  });

  factory ListBlog.fromJson(Map<String, dynamic> json) {
    return ListBlog(
      id: json['id'],
      user: json['user']['username'],
      avt: json['user']['avt'],
      comments: json['comments'],
      shares: json['shares'],
      likes: json['likes'],
      views: json['views'],
      content: json['content'],
      thumbnail: json['thumbnail'],
    );
  }
}

class HomeScreen extends StatelessWidget {
  Future<List<ListBlog>> fetchData() async {
    String url = LOCAL_HOST + ":" + PORT.toString();
    String? token = await storage.read(key: "accessToken");
    int page = 0;
    int size = 10;
    final response = await http.get(
      Uri.http(url, "api/v1/blogs", {'page': '$page', 'size': '$size'}),
      headers: {'Authorization': 'Bearer $token'},
    );
    var responseBody = response.body;
    if (response.statusCode == 200) {
      var jsonResponse = json.decode(response.body);
      List data = jsonResponse['content'];
      List<ListBlog> blogs = [];
      for (var item in data) {
        ListBlog blog = ListBlog.fromJson(item);
        blogs.add(blog);
      }
      print(blogs);
      return blogs;
      // return jsonResponse.map((data) => ListBlog.fromJson(data)).toList();
    } else {
      throw Exception('Unexpected error occured!');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Column(
        children: <Widget>[
          Container(
            margin: EdgeInsets.only(top: 40),
            padding: EdgeInsets.all(16),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  'Your Blog',
                  style: TextStyle(
                    fontSize: 24,
                    fontWeight: FontWeight.bold,
                  ),
                ),
              ],
            ),
          ),
          NewBlogForm(),
          Expanded(
            child: FutureBuilder<List<ListBlog>>(
              future: fetchData(),
              builder: (BuildContext context,
                  AsyncSnapshot<List<ListBlog>> snapshot) {
                if (snapshot.connectionState == ConnectionState.waiting) {
                  return Center(child: CircularProgressIndicator());
                } else if (snapshot.hasError) {
                  return Center(child: Text('Error: ${snapshot.error}'));
                }

                List<ListBlog> listBlog = snapshot.data!;

                return ListView.builder(
                  itemCount: listBlog.length,
                  itemBuilder: (BuildContext context, int index) {
                    ListBlog item = listBlog[index];
                    return BlogWidget(blog: item);
                  },
                );
              },
            ),
          ),
          Container(
            color: Colors.grey[300],
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceAround,
              children: [
                TextButton(
                  onPressed: () {
                    Navigator.push(
                      context,
                      MaterialPageRoute(builder: (context) => HomeScreen()),
                    );
                  },
                  child: Row(
                    children: [
                      Icon(Icons.home),
                    ],
                  ),
                ),
                IconButton(
                  icon: Icon(Icons.newspaper),
                  onPressed: () {
                    Navigator.push(
                      context,
                      MaterialPageRoute(builder: (context) => SearchTourArea()),
                    );
                  },
                ),
                IconButton(
                  icon: Icon(Icons.favorite),
                  onPressed: () {
                    Navigator.push(
                      context,
                      MaterialPageRoute(builder: (context) => FavoriteScreen()),
                    );
                  },
                ),
                IconButton(
                  icon: Icon(Icons.person),
                  onPressed: () {
                    Navigator.push(
                      context,
                      MaterialPageRoute(builder: (context) => ProfileScreen()),
                    );
                  },
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}

class BlogWidget extends StatelessWidget {
  final ListBlog blog;

  BlogWidget({required this.blog});

  @override
  Widget build(BuildContext context) {
    String img = blog.thumbnail.replaceAll("127.0.0.1", LOCAL_HOST);
    String avt = blog.avt.replaceAll("127.0.0.1", LOCAL_HOST);

    return Container(
      margin: EdgeInsets.symmetric(vertical: 8.0),
      padding: EdgeInsets.all(8.0),
      decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.circular(8.0),
        boxShadow: [
          BoxShadow(
            color: Colors.grey.withOpacity(0.5),
            spreadRadius: 2,
            blurRadius: 3,
            offset: Offset(0, 2),
          ),
        ],
      ),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Padding(
            padding: EdgeInsets.all(8.0),
            child: Text(
              blog.content,
              style: TextStyle(fontSize: 16.0),
            ),
          ),
          Image.network(
            img,
            fit: BoxFit.cover,
          ),
          SizedBox(height: 8.0),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Row(
                children: [
                  CircleAvatar(
                    radius: 30.0,
                    backgroundImage:
                    NetworkImage("${avt}"),
                    backgroundColor: Colors.transparent,
                  ),
                  SizedBox(
                    width: 10.0,
                  ),
                  Text(
                    '${blog.user}',
                    style: TextStyle(fontWeight: FontWeight.bold),
                  ),
                ],
              ),
              Row(
                children: [
                  Icon(Icons.thumb_up, color: Colors.blue),
                  SizedBox(width: 4.0),
                  Text('${blog.likes}'),
                  SizedBox(width: 8.0),
                  Icon(Icons.chat_bubble, color: Colors.blue),
                  SizedBox(width: 4.0),
                  Text('${blog.comments}'),
                  SizedBox(width: 8.0),
                  Icon(Icons.share, color: Colors.blue),
                  SizedBox(width: 4.0),
                  Text('${blog.shares}'),
                ],
              ),
            ],
          ),
        ],
      ),
    );
  }
}
