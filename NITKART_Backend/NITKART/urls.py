from django.contrib import admin
from django.urls import path
from rest_framework.authtoken import views
from django.conf.urls import include, url
from django.conf import settings
from rest_framework.urlpatterns import format_suffix_patterns
from django.conf.urls.static import static
from rest_framework.authtoken import views as rest_framework_views

urlpatterns = [
    url(r'^admin/', admin.site.urls),
    # path('user/api-token-auth/', views.obtain_auth_token),
    #url(r'^api-token-auth/', views.obtain_auth_token),
    # url(r'^get_auth_token/$',rest_framework_views.obtain_auth_token, name='get_auth_token'),
    url(r'^user/', include('user.urls')),
]

if settings.DEBUG:
    urlpatterns += static(settings.STATIC_URL, document_root = settings.STATIC_ROOT)
    urlpatterns += static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT)

urlpatterns = format_suffix_patterns(urlpatterns)
