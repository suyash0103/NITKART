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

