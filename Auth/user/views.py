from django.shortcuts import render, get_object_or_404
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from .models import Users
from .serializers import StockSerializer
from django.http import JsonResponse
from django.contrib.auth.models import User
from django.contrib.auth import authenticate
from django.db import transaction
from .forms import UserForm
# from rest_framework.authtoken.models import Token

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
        email_id = request.POST.get('email_id')
        phone_number = request.POST.get('phone_number')

        # Check is username already exists
        # if User.objects.filter(username=username).exists():
        #     return JsonResponse({'Error' : 'Username already exists'})

        user = Users()
        user.name = username
        user.password = password
        user.email_id = email_id
        user.phone_number = phone_number
        # token, created = Token.objects.get_or_create(user=user)
        # user.token = token
        user.save()
        # transaction.commit()

        # f = UserForm(request.POST)
        #
        # # Save a new Article object from the form's data.
        # new_article = f.save()

        # # Create a form to edit an existing Article, but use
        # # POST data to populate the form.
        # a = User.objects.get(pk=1)
        # f = UserForm(request.POST, instance=a)
        # f.save()

        if user.save():
            return JsonResponse({'Success' : 'Successfully registered'})
        return JsonResponse({'Error' : 'Sorry, not registered'})

class Login(APIView):

    def post(self, request):
        username = request.POST.get('username')
        password = request.POST.get('password')

        user = authenticate(username=username, password=password)
        if user is not None:
            if user.is_active:
               return JsonResponse({'Success' : 'Successfully Logged In'})
            else:
                return JsonResponse({'Error' : 'Account Disabled'})
        else:
            return JsonResponse({'Error' : 'Username and Password dont match'})
