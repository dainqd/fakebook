<?php

use App\Http\Controllers\ImageServeController;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider and all of them will
| be assigned to the "web" middleware group. Make something great!
|
*/

Route::get('upload-image', [ImageServeController::class, 'uploadImage'])->name('show.upload.image');
Route::post('upload-image', [ImageServeController::class, 'uploadImageMain'])->name('user.upload.image');
