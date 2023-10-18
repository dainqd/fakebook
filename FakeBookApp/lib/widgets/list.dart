import 'package:demo_app/widgets/home.dart';
import 'package:flutter/material.dart';
import './profile.dart';
import './favorite.dart';
import './search.dart';
import './detail.dart';
import '/utils/constants.dart';
import 'dart:async';
import 'dart:convert';
import 'package:http/http.dart' as http;

class EcoTourismSpot {
  final int id;
  final String thumbnail;
  final String name;
  final int star;
  final String address;
  final double price;

  EcoTourismSpot({
    required this.id,
    required this.thumbnail,
    required this.name,
    required this.star,
    required this.address,
    required this.price,
  });

  factory EcoTourismSpot.fromJson(Map<String, dynamic> json) {
    return EcoTourismSpot(
      id: json['id'],
      thumbnail: json['thumbnail'],
      name: json['name'],
      star: json['star'],
      address: json['address'],
      price: json['price'],
    );
  }
}

class ListTourArea extends StatelessWidget {
  Future<List<EcoTourismSpot>> fetchData() async {
    String url = LOCAL_HOST + ":" + PORT.toString();
    final response = await http.get(Uri.http(url, "/tour"));

    if (response.statusCode == 200) {
      List jsonResponse = json.decode(response.body);
      return jsonResponse.map((data) => EcoTourismSpot.fromJson(data)).toList();
    } else {
      throw Exception('Unexpected error occured!');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Khu Du Lịch Sinh Thái'),
      ),
      body: Column(
        children: <Widget>[
          Container(
            color: Colors.purple[50],
            padding: EdgeInsets.all(16),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  'Destination!',
                  style: TextStyle(
                    fontSize: 24,
                    fontWeight: FontWeight.bold,
                  ),
                ),
                SizedBox(height: 8),
                TextField(
                  decoration: InputDecoration(
                    hintText: 'Search...',
                    prefixIcon: Icon(Icons.search),
                  ),
                ),
              ],
            ),
          ),
          Expanded(
            child: FutureBuilder<List<EcoTourismSpot>>(
              future: fetchData(),
              builder: (BuildContext context,
                  AsyncSnapshot<List<EcoTourismSpot>> snapshot) {
                if (snapshot.connectionState == ConnectionState.waiting) {
                  return Center(child: CircularProgressIndicator());
                } else if (snapshot.hasError) {
                  return Center(child: Text('Error: ${snapshot.error}'));
                }

                List<EcoTourismSpot> ecoTourismSpots = snapshot.data!;

                return ListView.builder(
                  itemCount: ecoTourismSpots.length,
                  itemBuilder: (BuildContext context, int index) {
                    EcoTourismSpot item = ecoTourismSpots[index];
                    return Container(
                      padding: EdgeInsets.symmetric(vertical: 8),
                      margin: EdgeInsets.symmetric(horizontal: 24),
                      decoration: BoxDecoration(
                        border: Border.all(
                          color: Colors.grey,
                          width: 1,
                        ),
                      ),
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Image.network(
                            item.thumbnail,
                            fit: BoxFit.cover,
                            width: double.infinity,
                            height: 200,
                          ),
                          SizedBox(height: 8),
                          Padding(
                            padding: const EdgeInsets.symmetric(horizontal: 10),
                            child: Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                Text(
                                  item.name,
                                  style: TextStyle(
                                    fontSize: 20,
                                    fontWeight: FontWeight.bold,
                                  ),
                                ),
                                SizedBox(height: 4),
                                Text(
                                  'Số sao: ${item.star}',
                                  style: TextStyle(
                                    fontSize: 16,
                                  ),
                                ),
                                SizedBox(height: 4),
                                Text(
                                  'Địa chỉ: ${item.address}',
                                  style: TextStyle(
                                    fontSize: 16,
                                  ),
                                ),
                                SizedBox(height: 4),
                                Row(
                                  mainAxisAlignment:
                                      MainAxisAlignment.spaceBetween,
                                  children: [
                                    Text(
                                      'Giá:  \$${item.price.toStringAsFixed(2)}',
                                      style: TextStyle(
                                        fontSize: 18,
                                        color: Colors.red,
                                      ),
                                    ),
                                    Spacer(),
                                    ElevatedButton(
                                      onPressed: () {
                                        Navigator.push(
                                          context,
                                          MaterialPageRoute(
                                              builder: (context) =>
                                                  DetailScreen(id: item.id)),
                                        );
                                      },
                                      style: ElevatedButton.styleFrom(
                                        primary: Colors.purple,
                                      ),
                                      child: Text(
                                        'Chi tiết',
                                        style: TextStyle(
                                          color: Colors.white,
                                        ),
                                      ),
                                    ),
                                  ],
                                ),
                              ],
                            ),
                          ),
                        ],
                      ),
                    );
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
                      SizedBox(width: 8),
                      Text(
                        'Trang Chủ',
                        style: TextStyle(
                          fontSize: 16,
                        ),
                      ),
                    ],
                  ),
                ),
                IconButton(
                  icon: Icon(Icons.search),
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
