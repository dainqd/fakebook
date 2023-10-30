import 'package:customer_app/widgets/home.dart';
import 'package:flutter/material.dart';

import 'package:customer_app/utils/.constants.example.dart';

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
                    backgroundImage: NetworkImage("${avt}"),
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
