from django.shortcuts import render, get_object_or_404
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from .models import User
from .serializers import StockSerializer

# Create your views here.

class UserView(APIView):

    def get(self, request):
        items = User.objects.all()
        serializer = StockSerializer(items, many=True)
        return Response(serializer.data)

class Register(APIView):

    def post(self, request):
        username = request.POST.get('username')
        password = request.POST.get('password')
        email = request.POST.get('email_id')
        phone = request.POST.get('phone_number')
        user = User()
        user.name = username
        user.password = password
        user.phone_number = phone
        user.email_id = email
        user.save()